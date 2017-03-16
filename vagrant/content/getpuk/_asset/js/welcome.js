$(function () {

            parent.window.addEventListener('load', function () {
                setIframeHeight();
            });

            parent.window.addEventListener('resize', function () {
                setIframeHeight();
            });

            $(window).load(function () {
                setIframeHeight();
            });

            function getDocHeight() {
                return Math.max(
                    $(document).height(),
                    $(window).height(),
                    /* For opera: */
                    document.documentElement.clientHeight
                );
            }
            function setIframeHeight() {
                //console.log("height: " + getDocHeight());
                window.parent.document.getElementById('pukIframe').height = '100%';
                window.parent.document.getElementById('pukIframe').height = getDocHeight();
            }

            $('.puk-form input[name=mpn]').on('keydown blur', function () {
                $('.telErrorMessage').css('display', 'none');
                $('#MPN').css('border-color', '#d8d8d8');
                setIframeHeight();
            });

            $("#pukForm").submit(function (e) {
                var validate = phoneValidate();
                if (validate == false) {
                    e.preventDefault();
                    setIframeHeight();
                }
                else {
                    $("#pukForm")[0].submit();
                }
            });

            function validateUkPhoneNumber(number) {
                var re = /^(\+44|44|0044|0)7\d{9}$/;
                return re.test(number);
            }

            function phoneValidate() {
                var telVal = $.trim($('.puk-form input[name=mpn]').val());
                telInvalid = false;
                /*Tel Check*/
                if (telVal === '') {
                    telInvalid = true;
                    $('.telErrorMessage').text('Enter your mobile number to find your PUK code.');
                }
                else if (!validateUkPhoneNumber(telVal)) {
                    telInvalid = true;
                    $('.telErrorMessage').text("The mobile number you\'ve entered hasn\'t been recognised. Check your number and try again.");
                }

                if (telInvalid) {
                    $('.telErrorMessage').css('display', 'block');
                    $('.dvError').css('display', 'none');
                    $('#MPN').css('border-color', '#dd5858');
                    return false;
                }
                else {
                    $('.telErrorMessage').css('display', 'none');
                    return true;
                }
            }


        });