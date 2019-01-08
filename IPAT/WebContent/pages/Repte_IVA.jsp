<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

 <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
<div>

	<form  method="post" name="formInsertar"
		autocomplete="off" id="formActualizar" class="form-inline">
		<div class="form-group">
			<label for="datepickerA">De:</label> <input type="text"
				class="form-control" id="from" name="from" required style="width: 220px;">
		</div>
		<div class="form-group">
			<label for="datepickerB">A:</label> <input type="text"
				class="form-control" id="to" name="to" required style="width: 220px;">
		</div>

		<button type="submit" class="btn btn-default" />
		<!-- 		<input type="submit" id="Actualizar" value="Actualizar" /> -->
		
		 <button class="btn waves-effect waves-light" type="submit" name="action">
		 <i class="material-icons right">search</i>
		 Consultar
		 </button>

	</form>
</div>


  
        
<!--  	<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="../js/picker.js"></script>
    <script src="../js/picker.date.js"></script>
    <script src="../js/legacy.js"></script>
-->
    <script type="text/javascript">

    $('#from').datepicker({
    	dateFormat: "dd-mm-yy",
    });
    $('#to').datepicker({
    	dateFormat: "dd-mm-yy",
    	defaultDate : "+1w",
    	beforeShow : function() {
    		$(this).datepicker('option', 'minDate', $('#from').val());
    		if ($('#from').val() === '')
    			$(this).datepicker('option', 'minDate', 0);
    	}
    });
    </script>
</body>
</html>