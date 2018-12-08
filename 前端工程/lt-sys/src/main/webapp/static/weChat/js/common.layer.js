//*****************************************************************************
//以下代码为封装弹出层JS代码
//仅适用于IE8以上
//*****************************************************************************

var TipTimeNumber = 0;
JsCore.FrameLayer = function (idstring) {
    var Layer = new Object;
    var oBody = document.body;
    var oLayerBg = null;
    var oDiv = null;
    var x0 = 0, y0 = 0, x1 = 0, y1 = 0;
    var offx = 2, offy = 2;
    var topwidth = 0, topheight = 0;
    var winwidth = 650, winheight = 370;
    var pagewidth = 600, pageheight = 320;
    var frmpadding = 15;
    var isdraging = false;

    //属性定义
    Layer.LayerUrl = "about:blank";
    Layer.Scrolling = "auto";
    Layer.ShowClose = true;
    Layer.Width = 600;
    Layer.Height = 320;
    Layer.TipMode = false;
    Layer.TipFlag = 0;
    Layer.TipTitle = "";
    Layer.TipTextColor = "";
    Layer.TipText = "";
    Layer.IsBack = false;
    Layer.BackFrame = "";
    Layer.BackUrl = "";
    Layer.CloseFun = function () { }

    //初始化层
    Layer.open = function () {
        if (Layer.TipMode) {
            Layer.Width = 300;
            Layer.Height = 130;
        }

        //初始化内部变量
        winwidth = this.Width;
        winheight = this.Height + 50;
        pagewidth = this.Width;
        pageheight = this.Height;

        //获取浏览器窗口大小
        if (window.innerWidth)
            topwidth = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth))
            topwidth = document.body.clientWidth;
        if (window.innerHeight)
            topheight = window.innerHeight;
        else if ((document.body) && (document.body.clientHeight))
            topheight = document.body.clientHeight;
        if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
            topwidth = document.documentElement.clientWidth;
            topheight = document.documentElement.clientHeight;
        }

        //创建底部遮挡层
        oLayerBg = document.createElement("div");
        oLayerBg.id = "WinLayer" + idstring;
        oLayerBg.style.zIndex = GetMaxZIndex();
        oLayerBg.style.top = "0px";
        oLayerBg.style.left = "0px";
        oLayerBg.style.width = topwidth + "px";
        oLayerBg.style.height = (topheight + 45) + "px";
        oLayerBg.style.position = "fixed";

        oLayerBg.className = "transparent";
        var isback = this.IsBack;
        var backframe = this.BackFrame;
        var backurl = this.BackUrl;
        var thisLayer = this;

        oLayerBg.onclick = function () {
            if (isback) {
                if (backframe != "") {
                    var win = JsCore.$$(backframe);
                    var reurl = win.contentWindow.location.href;
                    if (backurl != "") reurl = backurl;
                    win.contentWindow.location.replace(reurl);
                } else {
                    var reurl = top.location.href;
                    if (backurl != "") reurl = backurl;
                    top.location.replace(reurl);
                }
            }
            thisLayer.CloseFun();
            JsCore.FrameLayerClose(idstring);
        }
        //创建弹出层外围层
        var oLayer = document.createElement("TABLE");
        oLayer.id = "Layer" + idstring;
        oLayer.style.zIndex = GetMaxZIndex() + 1;
        oLayer.border = "0px";
        oLayer.cellPadding = "0px";
        oLayer.cellSpacing = "0px";
        oLayer.style.width = winwidth + "px";
        oLayer.style.height = winheight + "px";
        oLayer.style.left = ((topwidth - winwidth) / 2) + "px";
        oLayer.style.top = ((topheight - winheight) / 2) + "px";
        oLayer.style.position = "fixed";

        //var oLayerTopRow = oLayer.insertRow(-1);
        //oLayerTopRow.insertCell(-1);
        //oLayerTopRow.insertCell(-1);
        //oLayerTopRow.insertCell(-1);

        var oLayerMainRow = oLayer.insertRow(-1);
        oLayerMainRow.insertCell(-1);
        oLayerMainRow.insertCell(-1);

        if (!this.TipMode) {

            //创建弹出层内部内容
            var oLayerTable = document.createElement("TABLE");
            oLayerTable.id = "LayerTable" + idstring;
            oLayerTable.border = "0px";
            oLayerTable.cellPadding = "0px";
            oLayerTable.cellSpacing = "0px";
            oLayerTable.style.width = (winwidth - 20) + "px";
            oLayerTable.style.height = (winheight - 20) + "px";

            var oLayerTableContentRow = oLayerTable.insertRow(-1);
            oLayerTableContentRow.id = "LayerContent" + idstring;
            oLayerTableContentRow.insertCell(-1);
            oLayerTableContentRow.cells[0].colSpan = "2";
            oLayerTableContentRow.cells[0].style.backgroundColor = "#fff";
            oLayerTableContentRow.cells[0].style.paddingTop = frmpadding + "px";
            oLayerTableContentRow.cells[0].style.paddingBottom = frmpadding + "px";
            oLayerTableContentRow.cells[0].style.paddingLeft = frmpadding + "px";
            oLayerTableContentRow.cells[0].style.paddingRight = frmpadding + "px";
            oLayerTableContentRow.cells[0].style.borderRadius = "5px";
            //---IFrame Begin--------------------------------------------
            var oIFrame = document.createElement("IFRAME");
            oIFrame.id = idstring + "Frame";
            oIFrame.name = idstring + "Frame";
            oIFrame.style.width = this.Width + 'px';
            oIFrame.style.height = (this.Height + 50) + 'px';
            oIFrame.src = this.LayerUrl;
            oIFrame.marginWidth = '0px';
            oIFrame.marginHeight = '0px';
            oIFrame.frameBorder = 0;
            oIFrame.scrolling = this.Scrolling;
            //---End-----------------------------------------------------
            oLayerTableContentRow.cells[0].appendChild(oIFrame);
            oIFrame = null;

            oLayerMainRow.cells[1].appendChild(oLayerTable);
            oLayerTableTitleRow = null;
            oLayerTableContentRow = null;
            oLayerTable = null;

        } else {

            //创建消息窗口
            var oLayerTable = document.createElement("TABLE");
            oLayerTable.border = "0px";
            oLayerTable.cellPadding = "0px";
            oLayerTable.cellSpacing = "0px";
            oLayerTable.style.width = "100%";
            oLayerTable.style.width = winwidth + "px";
            oLayerTable.style.height = winheight + "px";

            var oLayerTableRow = oLayerTable.insertRow(-1);
            oLayerTableRow.insertCell(-1);
            oLayerTableRow.cells[0].style.paddingTop = "35px";
            oLayerTableRow.cells[0].style.paddingBottom = "40px";
            oLayerTableRow.cells[0].style.paddingLeft = "50px";
            oLayerTableRow.cells[0].style.paddingRight = "50px";
            oLayerTableRow.cells[0].style.backgroundColor="#fff";
            oLayerTableRow.cells[0].style.borderRadius = "5px";

            var oTipDiv1 = document.createElement("DIV");
            oTipDiv1.style.fontSize = "20px";
            oTipDiv1.style.fontFamily = "Microsoft yahei,微软雅黑,Arial,Verdana,Tahoma,sans-serif";
            oTipDiv1.style.fontWeight = "bold";

            var oTipDiv2 = document.createElement("DIV");
            oTipDiv2.style.height = "50px";
            var oTipDiv21 = document.createElement("DIV");
            oTipDiv21.style.paddingTop = "10px";
            oTipDiv21.style.fontSize = "12px";
            oTipDiv21.style.lineHeight = "1.6";
            oTipDiv21.innerHTML = this.TipText;
            oTipDiv2.appendChild(oTipDiv21);

            var oTipDiv3 = document.createElement("DIV");
            oTipDiv3.style.marginTop = "10px";
            var oTipButton = document.createElement("INPUT");
            oTipButton.type = "button";
            oTipButton.className = "tipbutton";
            oTipButton.value = "关闭提示";
            var thisLayer = this;
            oTipButton.onclick = function () {
                thisLayer.CloseFun();
                JsCore.TipLayerClose(idstring);
            }
            oTipDiv3.appendChild(oTipButton);
            oTipButton = null;

            if (this.TipFlag == 1) {
                oTipDiv1.style.color = "#090";
                if (this.TipTextColor == "")
                    oTipDiv21.style.color = "#090";
                else
                    oTipDiv21.style.color = this.TipTextColor;
                if (this.TipTitle == "")
                    oTipDiv1.innerHTML = "提示：您的操作已经完成！";
                else
                    oTipDiv1.innerHTML = this.TipTitle;
                TipTimeNumber = 10;
            } else if (this.TipFlag == -1) {
                oTipDiv1.style.color = "#f00";
                if (this.TipTextColor == "")
                    oTipDiv21.style.color = "#f00";
                else
                    oTipDiv21.style.color = this.TipTextColor;
                if (this.TipTitle == "")
                    oTipDiv1.innerHTML = "提示：您的操作失败！";
                else
                    oTipDiv1.innerHTML = this.TipTitle;
                TipTimeNumber = 30;
            } else {
                oTipDiv1.style.color = "#000";
                if (this.TipTextColor == "")
                    oTipDiv21.style.color = "#000";
                else
                    oTipDiv21.style.color = this.TipTextColor;
                if (this.TipTitle == "")
                    oTipDiv1.innerHTML = "提示信息：";
                else
                    oTipDiv1.innerHTML = this.TipTitle;
                TipTimeNumber = 20;
            }

            oLayerTableRow.cells[0].appendChild(oTipDiv1);
            oLayerTableRow.cells[0].appendChild(oTipDiv2);
            oLayerTableRow.cells[0].appendChild(oTipDiv3);
            oLayerMainRow.cells[1].appendChild(oLayerTable);

            oLayerTableRow = null;
            oTipDiv1 = null;
            oTipDiv21 = null;
            oTipDiv2 = null;
            oTipDiv3 = null;
            oLayerTable = null;
        }

        oLayerMainRow.insertCell(-1);
        oLayerMainRow.cells[2].className = "midright";

        var oLayerBotRow = oLayer.insertRow(-1);
        oLayerBotRow.insertCell(-1);
        oLayerBotRow.cells[0].className = "botleft";
        oLayerBotRow.insertCell(-1);
        oLayerBotRow.cells[1].className = "botcenter";
        oLayerBotRow.insertCell(-1);
        oLayerBotRow.cells[2].className = "botright";

        oBody.appendChild(oLayerBg);
        oBody.appendChild(oLayer);

        if (oLayerBg) oLayerBg.style.display = 'block';
        if (oLayer) oLayer.style.display = 'block';

        oLayerTopRow = null;
        oLayerMainRow = null;
        oLayerBotRow = null;

        oLayerBg = null;
        oLayer = null;
        oBody = null;
    }

    return Layer;
}
JsCore.$Layer = function (layerId) {
    return JsCore.$$(layerId + "Frame").contentWindow;
}
JsCore.FrameLayerClose = function (idstring) {
    var oFrmc = JsCore.$$(idstring + "Frame");
    var obgFrmc = oFrmc.parentElement;
    obgFrmc.removeChild(oFrmc);
    oFrmc = null;
    obgFrmc = null;

    var oBodyc = document.body;
    var oDivc = JsCore.$$("Layer" + idstring);
    oDivc.focus();
    if (oDivc) oDivc.style.display = 'none';
    oBodyc.removeChild(oDivc);
    oDivc = null;

    var oDivBgc = JsCore.$$("WinLayer" + idstring);
    oDivBgc.focus();
    if (oDivBgc) oDivBgc.style.display = 'none';
    oBodyc.removeChild(oDivBgc);
    oDivBgc = null;
    oBodyc = null;
}

JsCore.TipLayerClose = function (idstring) {
    var oBodyc = document.body;
    var oDivc = JsCore.$$("Layer" + idstring);
    oDivc.focus();
    if (oDivc) oDivc.style.display = 'none';
    oBodyc.removeChild(oDivc);
    oDivc = null;

    var oDivBgc = JsCore.$$("WinLayer" + idstring);
    oDivBgc.focus();
    if (oDivBgc) oDivBgc.style.display = 'none';
    oBodyc.removeChild(oDivBgc);
    oDivBgc = null;
    oBodyc = null;
}

function GetMaxZIndex() {
    var MaxZ = 0;
    var ObjZ = 0;
    var objArr = document.getElementsByTagName("*");
    for (var i = 0; i < objArr.length; i++) {
        ObjZ = 0;
        if (objArr[i].className) {
            if (objArr[i].currentStyle) {
                ObjZ = parseInt(objArr[i].currentStyle.zIndex);
            }
            else {
                ObjZ = parseInt(window.getComputedStyle(objArr[i], null).zIndex);
            }
            if (!objArr[i].style.zIndex) {
                ObjZ = parseInt(objArr[i].style.zIndex);
            }
        }
        if (ObjZ > MaxZ) {
            MaxZ = ObjZ;
        }
    }
    return MaxZ;
}


