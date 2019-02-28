// var wshNetwork = new ActiveXObject("WScript.Network");
// document.write("域名       = "+ wshNetwork.UserDomain+"<br/>");
// document.write("计算机名   = "+ wshNetwork.ComputerName+"<br/>");
// document.write("登录用户名 = "+ wshNetwork.UserName+"<br/>");

function fun(obj){
    var tb = document.getElementById("EbInfoTable");
    // var wshNetwork = new ActiveXObject("WScript.Network");
    if (wshNetwork.UserName === "ASNPHTL") {
        if (obj.value ==="显示") {
            tb.setAttribute("hidden","hidden");
            obj.value = "显示";
        }else {
            tb.removeAttribute("hidden");
            obj.value = "隐藏";
        }
    }
}