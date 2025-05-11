$(function () {
    // Toggle the side navigation
    $("#sidebarToggle, #sidebarToggleTop").on('click', function (e) {
        $("body").toggleClass("sidebar-toggled");
        $(".sidebar").toggleClass("toggled");
        let current = $('#sidebarToggle').children(".bi");
        console.log(current)
        if(current.hasClass("bi-layout-sidebar-inset-reverse")) {
            current.removeClass("bi-layout-sidebar-inset-reverse");
            current.addClass("bi-layout-sidebar-inset");
        } else {
            current.removeClass("bi-layout-sidebar-inset");
            current.addClass("bi-layout-sidebar-inset-reverse");
        }
        if ($(".sidebar").hasClass("toggled")) {
            $('.sidebar .collapse').collapse('hide');
        }
    });
    // Close any open menu accordions when window is resized below 768px
    $(window).resize(function () {
        if ($(window).width() < 768) {
            $('.sidebar .collapse').collapse('hide');
        }
        // Toggle the side navigation when window is resized below 480px
        if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
            $("body").addClass("sidebar-toggled");
            $(".sidebar").addClass("toggled");
            $('.sidebar .collapse').collapse('hide');
        }
    });
    // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
    $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function (e) {
        if ($(window).width() > 768) {
            const e0 = e.originalEvent,
                delta = e0.wheelDelta || -e0.detail;
            this.scrollTop += (delta < 0 ? 1 : -1) * 30;
            e.preventDefault();
        }
    });
    // Scroll to top button appear
    $(document).on('scroll', function () {
        const scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });
    // Smooth scrolling using jQuery easing
    $(document).on('click', 'a.scroll-to-top', function (e) {
        const $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        e.preventDefault();
    });

    function loadingStart() {
        $("#loadingContent").css({
            //팝업창을 가운데로 띄우기 위해 현재 화면의 가운데 값과 스크롤 값을 계산하여 팝업창 CSS 설정
            "top": (($(window).height() - $("#loadingContent").outerHeight()) / 2 + $(window).scrollTop()) + "px",
            "left": (($(window).width() - $("#loadingContent").outerWidth()) / 2 + $(window).scrollLeft()) + "px"
        })
        $('body').css('overflow', 'hidden')
        loading.addClass('show')
        $('.mfp-loading.show').click(function () {
            alert("데이터를 불러오는 중입니다.")
        })
    }

    function loadingEnd() {
        $('body').css('overflow', 'auto')
        loading.removeClass('show');
    }

    function isToggle(data) {
        if (data.val() == 0) {
            if (!$(data).prev('.off').hasClass('active')) {
                $(data).prev('.off').toggleClass('active');
            }
            if ($(data).next('.on').hasClass('active')) {
                $(data).next('.on').toggleClass('active');
            }
        } else {
            if ($(data).prev('.off').hasClass('active')) {
                $(data).prev('.off').toggleClass('active');
            }
            if (!$(data).next('.on').hasClass('active')) {
                $(data).next('.on').toggleClass('active');
            }
        }
    }
});
