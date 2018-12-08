
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>支付平台</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/popup.js"></script>


</head>

<body>
<div class="header-top-box">
	<div class="container">
    	<div class="header-top-left">
        	<ul>
            	<li class="home">欢迎使用新型智慧城市服务平台</li>
            </ul>
        </div><!--/.header-top-left-->
        <div class="header-top-right">
        	<ul>
            	<li><a href="#" class="down">我要咨询</a></li>
                <li><a href="#" class="down">智能问答</a></li>
                <li><a href="#" class="down">常见问题</a></li>
            </ul>
        </div><!--/.header-top-right-->
    </div><!--/.container-->
</div><!--/.header-top-box-->

<div class="header-box-little">
	<div class="container">
    	<div class="logo-little">
        	<a href="index.html"><img src="images/logo.png" ></a>
        </div>
        <div class="pay-welcome">收银台</div>
        <div class="hot-tel">服务热线: 400-000-0000</div>
    </div><!--/.container-->
</div><!--/.header-box-little-->
<div class="shadow-bg"></div>

<div class="order-box">
	<ul class="order-thead">
    	<li class="t-name">缴费事项</li>
        <li class="t-id">订单编号</li>
        <li class="t-corporation">收费单位</li>
        <li class="t-money">缴费金额</li>
        <li class="t-press">操作</li>
    </ul><!--/.order-thead-->
    <ul class="order-tbody">
    	<li class="p-name">水费</li>
        <li class="p-id">139478654321</li>
        <li class="p-corporation">自来水公司</li>
        <li class="p-money">￥100.00</li>
        <li class="p-press"><a href="javascript:void(0)" onclick="changevalue_1()">查看详情<i class="icon-angle-down"></i></a></li>
    </ul><!--/.order-tbody-->
    <div class="hidden-more" id="tr_1" style="display:none;">
    	<ul>
        	<li><label>收费单位：</label>北京自来水公司</li>
            <li><label>用户编号：</label>010038</li>
            <li><label>订单编号：</label>139478654321</li>
            <li><label>缴费事项：</label>水费</li>
            <li><label>缴费账号：</label>100080</li>
            <li><label>缴费金额：</label>￥100.00</li>
        </ul>
    </div>
    <ul class="order-tbody">
    	<li class="p-name">电费</li>
        <li class="p-id">139478654321</li>
        <li class="p-corporation">电力局</li>
        <li class="p-money">￥100.00</li>
        <li class="p-press"><a href="javascript:void(0)" onclick="changevalue_2()">查看详情<i class="icon-angle-down"></i></a></li>
    </ul><!--/.order-tbody-->
    <div class="hidden-more" id="tr_2" style="display:none;">
    	<ul>
        	<li><label>收费单位：</label>北京自来水公司</li>
            <li><label>用户编号：</label>010038</li>
            <li><label>订单编号：</label>139478654321</li>
            <li><label>缴费事项：</label>水费</li>
            <li><label>缴费账号：</label>100080</li>
            <li><label>缴费金额：</label>￥100.00</li>
        </ul>
    </div>
    <ul class="order-tbody">
    	<li class="p-name">气费</li>
        <li class="p-id">139478654321</li>
        <li class="p-corporation">燃气公司</li>
        <li class="p-money">￥100.00</li>
        <li class="p-press"><a href="javascript:void(0)" onclick="changevalue_3()">查看详情<i class="icon-angle-down"></i></a></li>
    </ul><!--/.order-tbody-->
    <div class="hidden-more" id="tr_3" style="display:none;">
    	<ul>
        	<li><label>收费单位：</label>北京自来水公司</li>
            <li><label>用户编号：</label>010038</li>
            <li><label>订单编号：</label>139478654321</li>
            <li><label>缴费事项：</label>水费</li>
            <li><label>缴费账号：</label>100080</li>
            <li><label>缴费金额：</label>￥100.00</li>
        </ul>
    </div>
    <div class="order-tfoot">
    	<h1>合计：￥300.00</h1>
    </div><!--/.order-tfoot-->
</div><!--/.order-box-->

<div class="pay-box">
	<div class="tab-head">
    	<a href="javascript:void(0);" id="jm1" onclick="setTab('jm',1,3)" class="hover">扫码支付</a>
        <a href="javascript:void(0);" id="jm2" onclick="setTab('jm',2,3)">银行卡支付</a>
        <a href="javascript:void(0);" id="jm3" onclick="setTab('jm',3,3)">银联快捷支付</a>
    </div>
    <div class="tab-content">
    	<div id="con_jm_1" style="">
        	<div class="pay-left">
                <div class="pay-ewm">
                    <p><a href="pay-result.html"><img src="images/pay-ewm.jpg" ></a></p>
                </div><!--/.pay-ewm-->
            </div><!--/.pay-left-->
            <div class="pay-right">
                <div class="pay-ad">
                <p><img src="images/pay-ad.png" ></p>
                <p>说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字</p>
                <p>说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字</p>
                <p>说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字</p>
                </div>
            </div><!--/.pay-right-->
            <div class="clearfix"></div>
        </div><!--/.con_jm_1-->
        <div id="con_jm_2" style="display:none">
        	<ul class="pay-list">
                <li><a href="#"><img src="images/bank-logo01.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo02.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo03.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo04.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo05.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo06.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo07.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo08.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo09.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo10.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo11.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo12.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo15.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo16.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo17.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo18.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo19.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo20.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo21.jpg" /></a></li>
                <li><a href="#"><img src="images/bank-logo22.jpg" /></a></li>
        	</ul>
        </div><!--/.con_jm_2-->
        <div id="con_jm_3" style="display:none">
        	<ul class="pay-list">
                <li><img src="images/kuaijie.png" /></li>
                <li><a href="#"><img src="images/bank-logo01.jpg" /></a></li>
                <li>付款时通过银行预留手机短信确认付款</li>
        	</ul>
        </div><!--/.con_jm_3-->
    </div><!--/.tab-content-->
</div><!--/.pay-box-->


<div class="footer-box">
    <div class="footer">
    	<ul>
        	<li><a href="#">把本站设为首页</a></li>
            <li><a href="#">关于新型智慧城市服务平台</a></li>
            <li><a href="#">本站声明</a></li>
            <li><a href="#">新手指南</a></li>
            <li class="noline"><a href="#">联系我们</a></li>
        </ul>
    </div><!--/.footer-->
</div><!--/.footer-box-->

<div class="copyright-box">
    <div class="copyright">
    	<ul>
        	<li>
            	<ul>
                    <li>主办单位：某某省人民政府办公厅</li>
                    <li><a href="#">备案：京ICP备12000333号-2</a></li>
                    <li><a href="#"><img src="images/other1.png"  alt="京公网安备">京公网安备 33010602004556号</a></li>
                </ul>
                <p>中文域名：某某政务服务.政务 &nbsp; 建议使用1366*768分辨率/IE9.0或以上浏览器访问达到最佳效果</p>
            </li>
            <li>
            	<a href="#"><img src="images/other2.png"  alt="党政机关网站"></a>
                <a href="#"><img src="images/other3.png"  alt="政府网站找错"></a>
            </li>
        </ul>
  </div>
</div><!--/.copyright-box-->

<!--popup-->
<div id="goodcover" class="goodcover"></div>
<div id="code" class="code">
  <div class="close1"><a href="javascript:void(0)" id="closebt" class="closebt"><img src="images/close.png"></a></div>
  <ul class="pop">
    <li><img src="images/icon-alert.png" /></li>
    <li>
      <h2>超过当日限额</h2>
      <h5>5秒后自动关闭...</h5>
    </li>
  </ul>
</div>

</body>
</html>
