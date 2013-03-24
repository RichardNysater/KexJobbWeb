<%-- 
	Document   : main
	Created on : Mar 12, 2013, 2:15:34 PM
	Author	 : Richard Nysäter
<%=session.getAttribute("url")%>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
		<meta content="width=device-width, initial-scale=0.6" name="viewport">
		<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
		
		<script type='text/javascript' src="js/jquery.blockUI.js"></script>
		<script type='text/javascript' src="js/html5slider.js"></script>
		<script type='text/javascript' src="js/jquery-1.9.1.min.js"></script>
		<script src="js/audio.js"></script>
		
		<script>
			$(function() { 
				// Setup the player to autoplay the next track
				var a = audiojs.createAll({
					trackEnded: function() {
						var next = $('ol li.playing').next();
						if (!next.length) next = $('ol li').first();
						next.addClass('playing').siblings().removeClass('playing');
						audio.load($('a', next).attr('data-src'));
						audio.play();
					}
				});
		
				// Load in the first track
				var audio = a[0];
				
				first = $('ol a').attr('data-src');
				$('ol li').first().addClass('playing');
				audio.load(first);
				
				
				
			   
				ids = ['vol-0','vol-10', 'vol-20', 'vol-30', 'vol-40', 'vol-50', 'vol-60', 'vol-70', 'vol-80', 'vol-90', 'vol-100'];
				for (var i = 0, ii = ids.length; i < ii; i++) {
					var elem = document.getElementById(ids[i]),
					volume = ids[i].split('-')[1];
					elem.setAttribute('data-volume', volume / 100);
					elem.onclick = function(e) {
						audio.setVolume(this.getAttribute('data-volume'));
						e.preventDefault();
						return false;
					};
				}
				// Load in a track on click
				$('ol li').click(function(e) {
					e.preventDefault();
					$(this).addClass('playing').siblings().removeClass('playing');
					audio.load($('a', this).attr('data-src'));
					audio.play();
				});
				// Keyboard shortcuts
				$(document).keydown(function(e) {
					var unicode = e.charCode ? e.charCode : e.keyCode;
					// right arrow
					if (unicode === 32) {
						audio.playPause();
					}
				});
			});
			
			function showValue(newValue) {
				document.getElementById("range").innerHTML=newValue;
			}
			function showIt() {
				document.getElementById("vote").style.display = "block";
			}
			
			$(document).ready(function () {
				$('form').submit(function (e) {
					var form = this;
					e.preventDefault();
					setTimeout(function () {
						form.submit();
					}, 5000); // in milliseconds
					document.getElementById("voted").style.display = "block";
				});
			});
		</script>
		
		<title>Music compare</title>
		
	</head>
	<body onLoad='setTimeout("showIt()", 4000);'>
		<h1 id="main">Compare the songs</h1>
		<h2 class="example">First complete the example pairs as a warm up.</h2>
		<h2 class="example">An approximative rating will be shown after you answer.</h2>
			<div id ="maincont">
				<div class="wrapper">
					<p id="volume">Volume: <a href="#" id="vol-0">0%</a> | <a href="#" id="vol-10">10%</a> | <a href="#" id="vol-20">20%</a> | <a href="#" id="vol-30">30%</a> | <a href="#" id="vol-40">40%</a> | <a href="#" id="vol-50">50%</a> | <a href="#" id="vol-60">60%</a> | <a href="#" id="vol-70">70%</a> | <a href="#" id="vol-80">80%</a> | <a href="#" id="vol-90">90%</a> | <a href="#" id="vol-100">100%</a></p>
					<audio preload></audio>
					<ol>
						<li><a href="#" data-src="<%= session.getAttribute("songOneUrl") %>">Song id <%= session.getAttribute("songOne") %></a></li>
						<li><a href="#" data-src="<%= session.getAttribute("songTwoUrl") %>">Song id <%= session.getAttribute("songTwo") %></a></li>
					</ol>
					
				</div>
				<div id="vote">
					<form id="similarity" action="Submitted" >
						<input type="hidden" name="action" value="rateexample">
						
						<p>How similar are these two songs? (0 - 100)</p>
						<table>
							<tr>
								<td>
									<span id="range">50</span>
								</td>
								<td id="rangeslider">
									<input id="slider" type="range" min="0" max="100" step="1" value="50" name="rating" onchange="showValue(this.value);"/>
								</td>
							</tr>
							
						</table>
						<br>
						<input type="image" src="img/submit.gif" alt="Submit">
						<div id="voted">
							<p>A good similarity for this pair is somewhere <%= session.getAttribute("rating") %> </p>
							<p>A new pair of songs is being prepared...</p>
						</div>
					</form>
				</div>
			</div>
			<div id="shortcuts">
				<div>
					<h1>Keyboard shortcuts:</h1>
					<p><em>Space</em> Play/pause</p>
				</div>
			</div>
			</body>
			</html>
