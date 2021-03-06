<%-- 
    Document   : index
    Created on : 06-04-2015, 13:33:32
    Author     : EmilGras
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="style.css" rel="stylesheet">
        <title>JSP Pageeeee</title>
    </head>
    <body>
        
        <!---------- Nav bar ---------->
        <div class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="LoginServlet?action=loginPage" class="navbar-brand">Dell Business</a>
                </div>
                </div>
            </div>
        
        
        <!---------- Middle content ---------->
        <div class="container contentContainer">
            <div class="row">
                <div class="col-md-4 col-md-offset-4" id="topRow">
                    <img src="img/logo128px.png">
                    <p class="lead">The power to do more</p>
                    <p class="bold marginTop">If you are already registered then log in below otherwise click on the 'Become partner' button to join us now</p>
                    <h3 style="color: indianred">${loginErrorMessage}</h3>
                    <!---------- Log in ---------->
                    <form class="marginTop" action="LoginServlet" method="POST">
                        <input type="hidden" name="action" value="login">
                        <div class="input-group-lg ">
                            <input type="text" name="username" class="form-control marginTop" placeholder="Your username" />
                        </div>
                        <div class="input-group-lg ">
                            <input type="password" name="password" class="form-control marginTop" placeholder="Your password" />
                        </div>
                        <div class="input-group-lg ">
                            <input type="submit" value="Log in" class="btn btn-success btn-lg marginTop btn-block" />
                        </div>
                    </form>
                    
                    <!---------- Sign up ---------->
                    <div class="input-group-lg">
                        <a href="LoginServlet?action=signupPage" id="textDecorationNone"><input type="submit" value="Become partner" class="btn btn-info btn-lg marginTop btn-block"></a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
