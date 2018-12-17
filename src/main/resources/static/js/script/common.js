// $(document).ready(function(){
//     dropdownOpen();//调用
// });
// /**
//  * 鼠标划过就展开子菜单，免得需要点击才能展开
//  */
// function dropdownOpen() {
//
//     $(document).off('click.bs.dropdown.data-api');
//     var dropdownLi = $('li .dropdown');
//     dropdownLi.mouseover(function() {
//         $(this).addClass('open');
//     }).mouseout(function() {
//         $(this).removeClass('open');
//     });
// }

// Bootstrap提示信息中的默认值
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DEFAULT] = '提示';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_INFO] = '提示';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_PRIMARY] = '提示';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_SUCCESS] = '成功';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_WARNING] = '警告';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DANGER] = '严重';
BootstrapDialog.DEFAULT_TEXTS['OK'] = '确定';
BootstrapDialog.DEFAULT_TEXTS['CANCEL'] = '取消';
BootstrapDialog.DEFAULT_TEXTS['CONFIRM'] = '确认';