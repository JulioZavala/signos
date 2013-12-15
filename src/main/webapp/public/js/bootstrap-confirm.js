(function ($) {
    $.fn.extend({
        confirmModal: function (options) {
            var html = '<div class="modal" id="confirmContainer"><div class="modal-header"><a class="close" data-dismiss="modal">x</a>' +
            '<h3>#Heading#</h3></div><div class="modal-body">' +
            '#Body#</div><div class="modal-footer">' +
            '<a href="#" class="btn btn-primary" id="confirmYesBtn">Confirmar</a>' +
            '<a href="#" class="btn" data-dismiss="modal">Cerrar</a></div></div>';

            var defaults = {
                heading: 'Please confirm',
                body:'Body contents',
                callback : null
            };
            
            var options = $.extend(defaults, options);
            html = html.replace('#Heading#',options.heading).replace('#Body#',options.body);
            $(this).html(html);
            $(this).modal('show');
            var context = $(this); 
            $('#confirmYesBtn',this).click(function(){
                if(options.callback!=null)
                    options.callback();
                $(context).modal('hide');
            });
        }
    });

})(jQuery);