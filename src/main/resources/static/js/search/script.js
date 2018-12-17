$(document).ready(function(){	

	$("#select1 dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		if ($(this).hasClass("select-all")) {
			$("#selectA").remove();
		} else {
			var copyThisA = $(this).clone();
			var id = $(this).attr('action');

			if ($("#selectA").length > 0) {
				$("#selectA a").html($(this).text());
				$("#selectA input").attr('value',id);
			} else {
				$(".select-result dl").append(copyThisA.attr("id", "selectA"));
				$("#selectA").append('<input type="hidden" id="courseId" name="courseId" value="'+id+'" />');
			}
		}
	});
	
	$("#select2 dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		if ($(this).hasClass("select-all")) {
			$("#selectB").remove();
		} else {
			var copyThisB = $(this).clone();
			var id = $(this).attr('action');
			if ($("#selectB").length > 0) {
				$("#selectB a").html($(this).text());
				$("#selectB input").attr('value',id);
			} else {
				$(".select-result dl").append(copyThisB.attr("id", "selectB"));
				$("#selectB").append('<input type="hidden" id="sceneId" name="sceneId" value="'+id+'" />');
			}
		}
	});
	
	$("#select3 dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		if ($(this).hasClass("select-all")) {
			$("#selectC").remove();
		} else {
			var copyThisC = $(this).clone();
			var id = $(this).attr('action');
			if ($("#selectC").length > 0) {
				$("#selectC a").html($(this).text());
				$("#selectC input").attr('value',id);
			} else {
				$(".select-result dl").append(copyThisC.attr("id", "selectC"));
				$("#selectC").append('<input type="hidden" id="themeId" name="themeId" value="'+id+'" />');
			}
		}
	});

    $(document).on("click","#selectA",function () {
		$(this).remove();
		$("#select1 .select-all").addClass("selected").siblings().removeClass("selected");
	});

    $(document).on("click","#selectB",function () {
		$(this).remove();
		$("#select2 .select-all").addClass("selected").siblings().removeClass("selected");
	});

    $(document).on("click","#selectC",function () {
		$(this).remove();
		$("#select3 .select-all").addClass("selected").siblings().removeClass("selected");
	});

    $(document).on("click",".select dd",function () {
		if ($(".select-result dd").length > 1) {
			$(".select-no").hide();
		} else {
			$(".select-no").show();
		}
	});
	
});