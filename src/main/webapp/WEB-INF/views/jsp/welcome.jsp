<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring MVC 4 + Ajax Hello World</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Spring 4 MVC Ajax Hello World</a>
        </div>
    </div>
</nav>

<div class="container" style="min-height: 500px">

    <div class="starter-template">
        <h1>Search Form</h1>
        <br>

        <div id="feedback"></div>

        <form class="form-horizontal" id="search-form">
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">customer</label>
                <div class="col-sm-10">
                    <input type=text class="form-control" id="customer">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">ccyPair</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="ccyPair">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">type</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="type">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">direction</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="direction">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">tradeDate</label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="tradeDate">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">amount1</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="amount1">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">amount2</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="amount2">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">rate</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="rate">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">valueDate</label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="valueDate">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">legalEntity</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="legalEntity">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">trader</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="trader">
                </div>
            </div>


            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" id="bth-search"
                            class="btn btn-primary btn-lg">Validate</button>
                </div>
            </div>
        </form>

    </div>

</div>

<div class="container">
    <footer>
        <p>
            &copy; <a href="http://www.mkyong.com">Mkyong.com</a> 2015
        </p>
    </footer>
</div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
    jQuery(document).ready(function($) {

        $("#search-form").submit(function(event) {

            // Disble the search button
            enableSearchButton(false);

            // Prevent the form from submitting via the browser.
            event.preventDefault();

            searchViaAjax();

        });

    });

    function searchViaAjax() {

        var search = {}
        search["customer"] = $("#customer").val();
        search["ccyPair"] = $("#ccyPair").val();
        search["tradeDate"] = $("#tradeDate").val();
        search["valueDate"] = $("#valueDate").val();

        $.ajax({
            type : "POST",
            contentType : "application/json",
            // url : "search/api/getSearchResult",
            url : "/welcome",
            data : JSON.stringify(search),
            dataType : 'json',
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                display(data);
            },
            error : function(e) {
               console.log("ERROR: ", e);
                display(e);
                // alert(JSON.stringify(e));
            },
            done : function(e) {
                console.log("DONE");
                enableSearchButton(true);
            }
        });

    }

    function enableSearchButton(flag) {
        $("#btn-search").prop("disabled", flag);
    }

    function display(data) {
        var json = "<h4>Ajax Response</h4><pre>"
            + JSON.stringify(data, null, 4) + "</pre>";
        $('#feedback').html(json);
    }
</script>

</body>
</html>