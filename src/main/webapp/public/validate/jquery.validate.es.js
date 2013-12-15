jQuery.extend(jQuery.validator.messages, {
    required: "Campo requerido.",
    email: "Ingrese una direcci&oacute;n de correo valida.",
    date: "Ingrese una fecha valida.",
    number: "Ingrese un n&uacute;mero valido.",
    minlength: jQuery.validator.format("Ingrese al menos {0} d&iacute;gitos."),
    equalTo: "Ingrese el valor nuevamente",
    
    remote: "Please fix this field.",
    url: "Please enter a valid URL.",
    dateISO: "Please enter a valid date (ISO).",
    digits: "Please enter only digits.",
    creditcard: "Please enter a valid credit card number.",
    accept: "Please enter a value with a valid extension.",
    maxlength: jQuery.validator.format("Please enter no more than {0} characters."),
    rangelength: jQuery.validator.format("Please enter a value between {0} and {1} characters long."),
    range: jQuery.validator.format("Please enter a value between {0} and {1}."),
    max: jQuery.validator.format("Please enter a value less than or equal to {0}."),
    min: jQuery.validator.format("Please enter a value greater than or equal to {0}.")
});