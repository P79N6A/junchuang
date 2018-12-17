/**
 * Created by yanfzhang on 2018/1/21.
 */
$(function(){
    // nav收缩展开
    $('.navMenu li a').on('click',function(){
        var parent = $(this).parent().parent();//获取当前页签的父级的父级
        var labeul =$(this).parent("li").find(">ul")
        if ($(this).parent().hasClass('open') == false) {
            //展开未展开
            parent.find('ul').slideUp(300);
            parent.find("li").removeClass("open")
            parent.find('li a').removeClass("active").find(".arrow").removeClass("open")
            $(this).parent("li").addClass("open").find(labeul).slideDown(300);
            $(this).addClass("active").find(".arrow").addClass("open")
        }else{
            $(this).parent("li").removeClass("open").find(labeul).slideUp(300);
            if($(this).parent().find("ul").length>0){
                $(this).removeClass("active").find(".arrow").removeClass("open")
            }else{
                $(this).addClass("active")
            }
        }
    });

    var pathname = window.location.pathname;
    var parent = $(".navMenu");
    // var url=$("#cns").arrt("href");
    $(".navMenu > li > a").each(function(){
        if($(this).attr('href') == pathname) {
            var labeul =$(this).parent("li").find(">ul")
            parent.find('ul').slideUp(300);
            parent.find("li").removeClass("open")
            parent.find('li a').removeClass("active").find(".arrow").removeClass("open")
            $(this).parent("li").addClass("open").find(labeul).slideDown(300);
            $(this).addClass("active").find(".arrow").addClass("open")
            return;
        }
    });

    $(".sub-menu > li > a").each(function(){
        if($(this).attr('href') == pathname) {
            console.log($(this).attr('href'));
            var labeul =$(this).parent("li").find(">ul")

            $(this).parent("li").removeClass("open").find(labeul).slideUp(300);
            $(this).parent("li").parent(".sub-menu").parent("li").find(".arrow").addClass("open");
            $(this).addClass("active")
            $(this).parent("li").parent(".sub-menu").css('display','block');
            $(this).parent("li").parent(".sub-menu").parent("li").addClass("open");
            return;
        }
    });
});
