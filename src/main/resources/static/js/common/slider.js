/**
 * rebuild by YFZX-WB on 2017/07/20.
 */
define(['jquery'], function ($) {

    $(function () {

        if ($('#search-panel')[0]) {

            $('.search-remove').on('click', function () {
                $('#search-panel').fadeOut(300, function () {
                    $('#content-panel').removeClass().addClass('col-xs-12 col-md-12');
                    $('.search-show').show();
                });
            });

            $('.search-show').on('click', function () {
                $('#content-panel').removeClass().addClass('col-xs-10 col-md-10');
                $('#search-panel').fadeIn(300);
                $(this).hide();
            });

            require(['datetimepicker'], function () {
                var date = new Date();
                $.datetimepicker.setLocale('ch');

                $('#endTime').datetimepicker({
                    step: 1,
                    value: date,
                    format: 'Y-m-d H:i'
                });

                $('#startTime').datetimepicker({
                    step: 1,
                    value: date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(),
                    format: 'Y-m-d H:i'
                });
            });

            $('.advanced-search dt').click(function () {
                $(this).siblings('dd').toggle('normal');
            });
        }

    });

    function initSliderBox () {
        if ($('.slider-box').length <= 0) {
            $('body').append(
                '<div class="slider-box">'
                    + '<div class="slider-content">'
                        + '<div class="slider-title"><h3 id="sliderTitle"></h3></div>'
                        + '<div class="slider-body">'
                            + '<table id="sliderTable" class="table table-striped table-hover table-bordered"></table>'
                            + '<div id="sliderDetail" class="text-center"></div>'
                        + '</div>'
                    + '</div>'
                + '</div>'
            );
        }
        $('.slider-box').css({'min-height': $(document).height(), 'overflow-y': 'auto'}).click(function (e) {
            e.stopPropagation();
        });
        $(document).on('click', function () {
            if ($('.slider-box').is(':visible')) {
                $('.slider-box').fadeOut(300, function () {
                    clearSliderBox();
                });
            }
        });
    }

    function clearSliderBox() {
        if ($.fn.DataTable.isDataTable('#sliderTable')) {
            $('#sliderTable').dataTable().api().destroy();
        }
        $('#sliderTitle').text('');
        $('#sliderTable').empty();
        $("#sliderDetail").empty();
    }

    return {
        basePath: null,
        init: function (basePath) {
            this.basePath = basePath;
            initSliderBox();
        },
        fadeIn: function (callback, rowData) {
            var basePath = this.basePath;
            $('.slider-box').fadeIn(300, function () {
                clearSliderBox();
                callback(rowData, basePath);
            });
        }
    };
});