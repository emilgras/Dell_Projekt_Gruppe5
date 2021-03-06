<%-- 
    Document   : campaigns_partner
    Created on : 06-04-2015, 20:00:13
    Author     : EmilGras
Emil & Anders har arbejdet på denne JSP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="style.css" rel="stylesheet">
    </head>
    <body>
        <!------------------- NAV -------------------->
        <div class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="PartnerServlet?action=dashboard" class="navbar-brand">User Website</a>
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>        
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="PartnerServlet?action=dashboard"><img src="img/home24.png"> Dashboard</a></li>                   
                        <li class="active"><a href="PartnerServlet?action=newcampaign"><img src="img/newcampaign24.png"> New Campaign</a></li>   
                    </ul>
                    <a href="LoginServlet?action=logout">
                        <button id="logoutBtn" type="submit" class="btn btn-warning navbar-right">Log out</button> 
                    </a>
                </div>
            </div>
        </div>  

        <!---------------- Formular ---------------->
        <div class="container">
            <div class="col-md-6  col-md-offset-3">
                <h1 class="tileHeader">New campaign</h1>
                <h3 style="color: indianred">${campaignErrorMessage}</h3>
                <form class="form-group marginTop" action="PartnerServlet" method="post">
                    <div class="form-group">
                        <label for="date" >Campaign start:</label>
                        <input type="date" name="campaignstart" value="${campaign.start_date}" class="form-control" id="date">
                    </div>
                    <div class="form-group">
                        <label for="date" >Campaign end:</label>
                        <input type="date" name="campaignend" value="${campaign.end_date}" class="form-control" id="date">
                    </div>
                    <div class="form-group">
                        <label for="price" class="left-align">Price estimate EUR:</label>
                        <input type="number" name="price" value="" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="comment">Description:</label>
                        <textarea name="description" value="${campaign.description}" class="form-control" rows="5" id="comment"></textarea>
                    </div>
                    <div class="form-group marginTop">
                        <input type="submit" value="Send" class="btn btn-info btn-block">
                        <input type="hidden" name="action" value="sendcampaign">
                    </div>
                </form>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>   
    </body>
</html>
