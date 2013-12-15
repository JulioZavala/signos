package app.zelper.misc;

import app.zelper.enums.PrioridadTicket;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public class TypesUtil {

    /* DEFAULT VALUES */
    public static Integer getDefaultInt(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Integer.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        return null;
    }

    public static Integer getDefaultInt(Object objValue, Integer defaultInt) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Integer.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return defaultInt;
        }
        return defaultInt;
    }

    public static Long getDefaultLong(Object objValue) {
        if (objValue instanceof java.lang.Long) {
            return (Long) objValue;
        }
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString()) && !objValue.toString().equals("null")) {
                return Long.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return null;
    }

    public static Long getDefaultLong(BigDecimal bigDecimalValue) {
        try {
            if (bigDecimalValue != null) {
                return bigDecimalValue.longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
        return 0L;
    }

    public static Long getDefaultLong(Object objValue, Long defaultValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Long.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    public static BigDecimal getDefaultBigDecimal(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return BigDecimal.valueOf(Double.valueOf(objValue.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static BigDecimal getDefaultBigDecimal(Object objValue, double dblDefault) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return BigDecimal.valueOf(Double.valueOf(objValue.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal(dblDefault);
        }
        return new BigDecimal(dblDefault);
    }

    public static Float getDefaultFloat(Object objValue, Float defaultValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Float.parseFloat(objValue.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    public static Double getDefaultDouble(BigDecimal value) {
        try {
            if (value != null) {
                return value.doubleValue();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        return null;
    }

    public static double getDefaultDouble(Object objValue, double defaultValue) {
        try {
            if (objValue != null) {
                if (objValue instanceof BigDecimal) {
                    return ((BigDecimal) objValue).doubleValue();
                } else if (StringUtils.isNotBlank(objValue.toString())) {
                    return Double.parseDouble(objValue.toString());
                } else {
                    return defaultValue;
                }
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return defaultValue;
        }
    }

    public static boolean getDefaultBoolean(Object value) {
        boolean response = false;

        if ((Integer) value == 1) {
            response = true;
        } else {
            response = false;
        }

        return response;

    }

    public static String getDefaultString(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return objValue.toString();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        return null;
    }

    /* DATE AND TIMESTAMPS */
    /**
     * @param objValue Cadena que contiene la fecha
     * @param dateFormat formato de objValue
     * @return Timestamp
     */
    public static String getStringDate(Date date, String dateFormat) {
        if (date != null && StringUtils.isNotBlank(date.toString())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                String retorno = sdf.format(date);
                return retorno;
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        return null;
    }

    public static String getCurrentStringDate(String dateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            java.util.Date date = new Date();
            return sdf.format(date).toString();
        } catch (Exception e) {
            //  e.printStackTrace();
            return null;
        }
    }

    public static Timestamp getCurrentTimestamp() {
        try {
            return new Timestamp(new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDefaultString(Timestamp fecha, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ES"));
        return sdf.format(fecha);
    }

    public static Long getTimestamp(Date fecha, String hora) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (hora.equals("")) {
            hora = "00:00";
        }
        String fechaTexto = df.format(fecha) + " ";
        return dfh.parse(fechaTexto + hora).getTime();
    }

    public static Long getUnixTime() {
        try {
            return System.currentTimeMillis() / 1000L;
        } catch (Exception e) {
            return null;
        }
    }

    /* RANDOM 5 DIGITOS */
    public static Integer getRandom() {
        Random rnd = new Random();
        return rnd.nextInt(900000) + 100000;

    }

    /* HASH TEXT */
    public static String getMD5(String pass) throws UnsupportedEncodingException {
        byte[] bytesOfMessage = pass.getBytes("UTF-8");
        String rtrn = DigestUtils.md5DigestAsHex(bytesOfMessage);
        return rtrn;
    }
    
    public static String getClean(String string) {
        try {
            String rtnr = Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replace(" ", "_").replace("_-_", "_");
            return rtnr;

        } catch (Exception e) {
            return null;
        }
    }
    
    public static String toUp(String string){
       return string.toUpperCase();
    }
    
    
    public static String getPrioridadTicket(Integer prioridad){
        return PrioridadTicket.get(prioridad).toString();
    }
}
