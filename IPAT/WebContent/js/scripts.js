/*
$(document).ready(function(){   
            $('#btnPrueba').click(function(){
                $.ajax({  
                    url:'../ImportarServlet',  
                    type:'post',
                    dataType: 'json',
                    success: function(data) {
                    	 var fName = document.getElementById('fName').value;
                    	 $("#ajaxResponse").html("");
                    	 $("#ajaxResponse").append(data.arregloTabla);
                    	 $("#divErrores").html("");
                    	 $("#divErrores").append(data.arregloErrores);
                    }  
                });  
            });  
            $.ajax({
                url: '../ImportarServlet',
                data: {
                    postVariableName: fName
                },
                type:'POST'
            }); 
});
*/