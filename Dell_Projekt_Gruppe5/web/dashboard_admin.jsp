<%-- Frederik, Emil og Anders har arbejdet på denne jsp. 
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Dell | Dashboard</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="style.css" rel="stylesheet">
    </head>
    <body>


        <!------------------- NAV ------------------ -->
        <section>
            <div class="navbar navbar-inverse navbar-static-top">
                <div class="container">
                    <div class="navbar-header">
                        <a href="AdminServlet?action=dashboard" class="navbar-brand">Admin Website</a>
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>        
                    </div>
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href=""><img src="img/home24.png"> Dashboard</a></li>                   
                            <li><a href="AdminServlet?action=campaigns"><img src="img/campaigns24.png"> Campaigns</a></li>
                            <li><a href="AdminServlet?action=partners"><img src="img/partners24.png"> All partners</a></li>     
                        </ul>
                        <a href="LoginServlet?action=logout">
                            <button id="logoutBtn" type="submit" class="btn btn-warning navbar-right">Log out</button>
                        </a>
                    </div>
                </div>
            </div> 
        </section>


        <section class="row">
            <div class="container  marginBottom">
                <div class="row">
                <div class="col-md-4"><h1 class="tileHeader">Dashboard</h1></div>
                <div class="col-md-3  col-md-offset-5"><label for="nqtr">New quarter budget:</label>
                    <input type="number" name="newQuarter" class="form-control" id="nqtr"> </div>
                </div>
                <h4 style="color: ffa500">${errorMessage}</h4>
                <!------------ PENDING PARTNERS ------------>
                <div class="col-md-6 marginTop">

                    <div>
                        <h3 class="tileHeader"><span class="glyphicon glyphicon-info-sign"></span> Pending Applicants</h3>
                        <div class="tile">
                            <div>
                                <table class="table table-striped table-bordered">
                                    <tr class="active"><th>Company</th><th>CVR</th><th>Country</th><th>Accept</th><th>Decline</th></tr>
                                            <c:forEach var="partner" items="${pendingPartners}">
                                        <tr><td>${partner.name} </td><td>${partner.cvr} </td><td>${partner.country}</td><td><button type="button"  id="pendingPartners" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button></td><td><button type="button" id="declinePartner" class="btn btn-danger btn-danger"><span class="glyphicon glyphicon-remove"></span></button></td></tr>
                                                </c:forEach>
                                </table> 
                            </div>
                        </div>
                    </div>

                    <!------------ PENDING CAMPAIGNS ------------>
                    <div class="topMargin">                  
                        <h3 class="tileHeader"><span class="glyphicon glyphicon-info-sign"></span> Pending Campaigns</h3>
                        <div class="tile">
                            <div>                     
                                <table class="table table-striped table-bordered">
                                    <tr class="active"><th>Company</th><th>Status</th><th>Accept</th><th>Decline</th><th>Detail</th></tr>
                                            <c:forEach var="campaign" items="${pendingCampaigns}">
                                        <tr>
                                            <td>${campaign.name} </td>
                                            <td>${campaign.status} </td>
                                            <td><button type="button"  id="pendingCampaignsAccept" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button></td>
                                            <td>
                                                <c:if test="${campaign.status == 'Pending'}"><button type="button" id="pendingCampaignsDeclineCampaign" class="btn btn-danger btn-danger"><span class="glyphicon glyphicon-remove"></span></button></c:if>
                                                <c:if test="${campaign.status == 'POE Pending'}"><button type="button" id="pendingCampaignsDeclinePoe" class="btn btn-danger btn-danger"><span class="glyphicon glyphicon-remove"></span></button></c:if>
                                                <c:if test="${campaign.status == 'Invoice Pending'}"><button disabled="disabled" type="button" class="btn btn-danger btn-danger"><span class="glyphicon glyphicon-remove"</span></button></c:if>
                                                </td>
                                                <td><input id="pendingCampaignsDetail" type="button" class="btn btn-info" value="View campaign"></td>
                                            </tr>
                                    </c:forEach>                                 
                                </table> 
                            </div>
                        </div>
                    </div>

                </div>

                <!------------ NEWEST CAMPAIGNS ------------>
                <div class="col-md-6 marginTop">
                    <a href="AdminServlet?action=campaigns" id="textDecorationNone"><h3 class="tileHeader"><span class="glyphicon glyphicon-list"></span> Newest Campaigns</h3> </a>
                    <div class="tile">
                        <div>
                            <table class="table table-striped table-bordered">
                                <tr class="active"><th>Nr.</th><th>Company</th><th>Price Eur</th><th>Created</th><th>Status</th><th>Detail</th></tr>
                                        <c:forEach var="campaign" items="${newestCampaigns}" begin="0" end="4">
                                    <tr class="tablerow"><td></td><td>${campaign.name}</td><td>${campaign.price}</td><td>${campaign.created_date}</td><td>${campaign.status}</td><td><input id="newestCampaigns" type="button" class="btn btn-info" value="View campaign"></td></tr>

                                </c:forEach>
                            </table> 


                        </div>
                    </div>
                </div>



            </div>
        </section>


        <section class="row statsSection">
            <div class="container marginBottom">
                <div class="col-md-12 stats">

                    <div class="col-md-4 center">
                        <h2 class="whiteText">Totel Partners</h2>
                        <h1 class="whiteText">${countPartners}</h1>
                    </div>
                    <div class="col-md-4 center">
                        <h2 class="whiteText">Total Campaigns</h2>
                        <h1 class="whiteText">${countCampaigns}</h1>
                    </div>
                    <div class="col-md-4 center">
                        <h2 class="whiteText">Total Countries</h2>
                        <h1 class="whiteText">${countCountries}</h1>
                    </div>
                </div>
            </div>
        </section>

        <section class="row">
            <div class="container marginBottom">
                <div class="col-md-3">
                    <h3>Quater budget:  ${startsBelob} EUR</h3>

                    <h3>Estimatet use:  ${nuvaerendeBelob} EUR</h3>

                </div>
                <div class="col-md-9" id="piechart_3d" style="width: 675px; height: 375px;"></div> 
            </div>

        </section>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script src="scriptAdmin.js"></script>

        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript">
            google.load("visualization", "1", {packages: ["corechart"]});
            google.setOnLoadCallback(drawChart);
            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                    ['Task', 'Hours per Day']
            <c:forEach var="item" items="${prices}">
                    , ['${item.partnerName} ${item.price} EUR', ${item.price}]
            </c:forEach>
                ]);

                var options = {
                    backgroundColor: 'transparent',
                    title: 'Campaigns',
                    is3D: true,
                };

                var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
                chart.draw(data, options);
            }
        </script>


    </body>
</html>

