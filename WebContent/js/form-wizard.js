var FormWizard = function () {


    return {
        //main function to initiate the module
        init: function () {
            if (!jQuery().bootstrapWizard) {
                return;
            }

            function format(state) {
                if (!state.id) return state.text; // optgroup
                return "<img class='flag' src='../../assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
            }

            $("#country_list").select2({
                placeholder: "Select",
                allowClear: true,
                formatResult: format,
                formatSelection: format,
                escapeMarkup: function (m) {
                    return m;
                }
            });

            var form = $('#batch_form');
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);
            

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    //account
                    subject: {
                        required: true
                    },
                    startingdt: {
                        required: true
                    },
                    duration: {
                        required: true,
                    },
                    btype: {
                        required: true,
                    },
                    username: {
                        required: true
                    },
                    password: {
                        minlength: 5,
                        required: true
                    }
                },

                /*messages: { // custom messages for radio buttons and checkboxes
                    'payment[]': {
                        required: "Please select at least one option",
                        minlength: jQuery.validator.format("Please select at least one option")
                    }
                },*/

               errorPlacement: function (error, element) { // render error placement for each input type
                    if (element.attr("name") == "gender") { // for uniform radio buttons, insert the after the given container
                        error.insertAfter("#form_gender_error");
                    } else if (element.attr("name") == "payment[]") { // for uniform radio buttons, insert the after the given container
                        error.insertAfter("#form_payment_error");
                    } else {
                        error.insertAfter(element); // for other inputs, just perform default behavior
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit   
                    success.hide();
                    error.show();
                    Metronic.scrollTo(error, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    if (label.attr("for") == "gender" || label.attr("for") == "payment[]") { // for checkboxes and radio buttons, no need to show OK icon
                        label
                            .closest('.form-group').removeClass('has-error').addClass('has-success');
                        label.remove(); // remove error label here
                    } else { // display success icon for other inputs
                        label
                            .addClass('valid') // mark the current input as valid and display OK icon
                        .closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                    }
                },

                submitHandler: function (form) {
                    success.show();
                    error.hide();
                    //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
                }

            });

            var displayConfirm1 = function() {
                $('#tab2 .form-control-static', form).each(function(){
                    var input = $('[name="'+$(this).attr("data-display")+'"]', form);
                 /*   if (input.is(":radio")) {
                        input = $('[name="'+$(this).attr("data-display")+'"]:checked', form);
                    }*/
                    if (input.is(":text") || input.is("textarea")) {
                        $(this).html(input.val());
                    } else if (input.is("select")) {
                        $(this).html(input.find('option:selected').text());
                    }
                    /*else if (input.is(":radio") && input.is(":checked")) {
                        $(this).html(input.attr("data-title"));
                    }*/
                    /*else if ($(this).attr("data-display") == 'payment') {
                        var payment = [];
                        $('[name="payment[]"]:checked').each(function(){
                            payment.push($(this).attr('data-title'));
                        });
                        $(this).html(payment.join("<br>"));
                    }*/
                });
            }

            var handleTitle1 = function(tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_2')).text('Step ' + (index + 1) + ' of ' + total);
                // set done steps
                jQuery('li', $('#form_wizard_2')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#form_wizard_2').find('.button-previous').hide();
                } else {
                    $('#form_wizard_2').find('.button-previous').show();
                }
                if(current==2)
                {
                	displayConfirm1();
                }
                if (current >= total) {
                    $('#form_wizard_2').find('.button-next').hide();
                    $('#form_wizard_2').find('.button-submit').show();
                } else {
                    $('#form_wizard_2').find('.button-next').show();
                    $('#form_wizard_2').find('.button-submit').hide();
                }
                Metronic.scrollTo($('.page-title'));
            }

            // default form wizard
            $('#form_wizard_2').bootstrapWizard({
                'nextSelector': '.button-next',
                'previousSelector': '.button-previous',
                onTabClick: function (tab, navigation, index, clickedIndex) {
                    return false;
                    /*
                    success.hide();
                    error.hide();
                    if (form.valid() == false) {
                        return false;
                    }
                    handleTitle1(tab, navigation, clickedIndex);
                    */
                },
                onNext: function (tab, navigation, index) {
                    success.hide();
                    error.hide();

                    if (form.valid() == false) {
                        return false;
                    }

                    handleTitle1(tab, navigation, index);
                },
                onPrevious: function (tab, navigation, index) {
                    success.hide();
                    error.hide();

                    handleTitle1(tab, navigation, index);
                },
                onTabShow: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#form_wizard_2').find('.progress-bar').css({
                        width: $percent + '%'
                    });
                }
            });

            $('.hrs').on('change',function(){
            	var x1=document.batch_form.days.value;
            	var x2=(document.batch_form.duration.value).split(":");
            	var x3=(x1*(parseInt(x2[0]*60)+parseInt(x2[1])));
            	x3=x3/60;
            	$("#totalhr").val(x3);
            });
            $('.hrs1').on('click',function(){
            	var x1=document.batch_form.days.value;
            	var x2=(document.batch_form.duration.value).split(":");
            	var x3=(x1*(parseInt(x2[0]*60)+parseInt(x2[1])));
            	x3=x3/60;
            	$("#totalhr").val(x3);
            });
            
            $('.amount').on('change',function(){
            	var bp=parseFloat(document.batch_form.bprice.value);
            	var dis=parseFloat(document.batch_form.discount.value);
            	var tax=parseFloat(document.batch_form.tax.value);
            	var disprice=((bp*dis)/100);
            	bp=bp-disprice;
            	var taxprice=((bp*tax)/100);
            	$('#bpp').val(bp);
            	$('#taxinr').val(taxprice.toFixed(2));
            	$('#totalinr').val((bp+taxprice).toFixed(2));
            	$('#taxinr').val(taxprice.toFixed(2));
            	var p1=0.956,p2=0.31;
    			var p3=(1-(tax/100));
    			var usd=((((bp/p3)/61)-p2)/p1);
            	$('#totalusd').val(usd.toFixed(2));
            });
            
            $('#form_wizard_2').find('.button-previous').hide();
            $('#form_wizard_2 .button-submit').click(function () {
            	  document.batch_form.submit();
            	
            }).hide();
        }

    };

}();