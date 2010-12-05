<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
 <head>
  <title>RHoK:P2M - Phone Simulator</title>
  <link type="text/css" rel="stylesheet" href="css/style.css" />
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<style type="text/css">
label {
	width: 50px;
	display: block;
	float: left;
	font-weight: bold;
	color: white;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$('#btnRefresh').click(refreshHistory);
	$('form').submit(sendText);
});

function sendText() {
	params = $('form').serialize();
	$.post('/sms', params);
	alert('Sending Text Message...');
	
	// reset the form
	$('form').find('input').not(':button, :submit, :reset, :hidden').val('');
	
	return false;
}

function refreshHistory() {
	$.get('/sms', refreshHistoryDone, 'json');	
}
function refreshHistoryDone(data) {
	var html = '';
	for(var i = 0; i < data.length; i++) {
		var item = data[i];
		html += '[' + item.created + '] <b>' + item.to + '</b>: ' + item.message + '<br/>';
	}
	$('#log').html('<p>' + html + '</p>');
}
</script>
 </head>
  <body>

<h2>Phone Simulator</h2>

<form style="background: url('img/phone-bg.jpg'); width: 450px; height: 296px; float: left;">
 <div style="padding: 95px 0 0 160px;">
  <div>
   <label for="to">To:</label><select name="to" id="to">
    <option value="System">System</option>
   </select>
  </div>
  <div>
   <label for="from">From:</label><select name="from" id="from">
    <option value="+1 555-121-1111">Mark's Seeds</option>
    <option value="+1 555-121-1112">John's Seeds</option>
    <option value="+1 555-121-1113">Smith's Fertilizer</option>
    <option value="+1 555-121-1114">Thompson Chemicals</option>
    <option value="+1 555-121-1115">General Farm Supplies</option>
    <option value="+1 555-444-1111">Measurement Station</option>
   </select>
  </div>
  <textarea name="content" style="width: 200px; height: 120px;"></textarea>
  <div class="submit">
   <input type="submit" value="Send" />
  </div>
 </div>
</form>

<h2>Message Log</h2>
<input type="button" value="Refresh" id="btnRefresh" />
<div id="log">

</div>
  </body>
</html>