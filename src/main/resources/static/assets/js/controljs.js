$(document).ready(
    function () {
        $.ajax({
            url: "/regions",
            type: "GET",
            success: function (regions) {
                // $('#region').empty();
                $.each(regions, function (i, region) {
                    $('#region').append($("<option/>", {
                        value: region.id,
                        text: region.name
                    }));
                });
            },
            error: function (jqXHR, exception) {
                console.log();
            }
        })
        $('#region').change(function () {
            var id = this.value;
            console.log(id)
            getDistrict(id)
        })
    })

function getDistrict(id) {
    $.ajax({
        url: "/districts",
        data: {regionId: id},
        type: "GET",
        success: function (districts) {
            $('#district').empty();
            $.each(districts, function (i, district) {
                $('#district').append($("<option/>", {
                    value: district.id,
                    text: district.name
                }));
            });
        },
        error: function (jqXHR, exception) {
            console.log(error);
        }
    })
}
$(document).ready(function () {

    // add address regions
    $("#address").click(function () {
        $.ajax({
            type:'GET',
            url:"/districts/" + parseInt($("#region").val())+ "/" +parseInt($("#district").val()),
            async:false,
            success:function (order){
                var msg = '';
                $('#checkAddress').html(msg);
            }
        })
    });

// get my books

    $("#getMyBooks").click(function () {
        $.ajax({
            type:'GET',
            url:"/actionBook/myBooks",
            async:false,
            success:function (data){
                var div1 = document.getElementById('defaultAllBooks');
                var div2 = document.getElementById('myBooks');
                var div3 = document.getElementById('mySearchedBooks');
                div1.style.display = 'none';
                div2.style.display = 'contents';
                div3.style.display = 'none';

                // style='width:300px;\n" +
                // "  height:400px; object-fit:cover;'
                var myBooks = $("#myBooks");
                $("#myBooks").empty();
                $("#mySearchedBooks").empty();
                $("#allBooks").empty();
                for (i = 0; i < data.length; i++) {
                    var bookId = data[i].bookid;
                    myBooks.append("<div class='card'>" +
                        "<div class='img'>"+
                        '<img  src="data:image/jpg;base64,'+data[i].picture+'"/>'+"</div>"+
                        "<div class='content'>" +
                        "<p class='title' style='text-transform: uppercase'> " + data[i].name + "</p>"           +
                        "<h5 class='copy'> " +'+'+ data[i].username + "</h5>"+
                        "<h5 class='copy' style='text-transform: capitalize'> " + data[i].author + "</h5>"+
                        "<h5 class='copy'> " + data[i].language + "</h5>"+
                        "<h5 class='copy'> " + data[i].comment + "</h5>"+
                        "<h5 class='copy'> " + data[i].region + ' ' + data[i].district +"</h5>"+
                        // "<h5 class='copy'> " + data[i].district + "</h5>"+
                        // "<input type='button' class='card1-btn' value='O`zgartirish'>"+
                        "<input type='button' class='card2-btn' value='O`chirish' data-toggle='modal' data-target='#deleteBookModal'>"+
                        "</div></div>");
                        $("#deleteBook").click(function(){
                        $.ajax({
                            url:"/actionBook/deleteMyBook/"+parseInt(bookId),
                            type: "POST",
                            processData: false,
                            contentType: false,
                            success: function (result) {

                            }
                        });

                    });
                    $("#editBookModal").on("show.bs.modal", function(event){
                        // Place the returned HTML into the selected element
                        $(this).find(".modal-body").load("/actionBook/editBook/"+data[i].id)})
                }

            },
            error: function (jqXHR, exception) {
                console.log(jqXHR);
                getErrorMessage(jqXHR, exception);
            }
        })
    });



    $("#defaultAllBooks").click(function () {
        $.ajax({
            type:'GET',
            url:"/actionBook/allBooks",
            async:false,
            success:function (data){
                var div1 = document.getElementById('defaultAllBooks');
                var div2 = document.getElementById('myBooks');
                var div3 = document.getElementById('mySearchedBooks');
                div1.style.display = 'contents';
                div2.style.display = 'none';
                div3.style.display = 'none';

            }                           })
    });

});






<!--addMyBook-->








$(document).ready(function(){
    var input = document.getElementById('searchBook');
    input.addEventListener('input', searchBook);
    function searchBook(){
        jQuery.ajax({
            url:"/actionBook/book/"+$("#searchBook").val(),
            type: "get",
            processData: false,
            contentType: false,
            success: function (data) {
                var div1 = document.getElementById('defaultAllBooks');
                var div2 = document.getElementById('myBooks');
                var div3 = document.getElementById('mySearchedBooks');
                div1.style.display = 'none';
                div2.style.display = 'none';
                div3.style.display = 'contents';
                var myBooks = $("#mySearchedBooks");
                $("#allBooks").empty();
                $("#myBooks").empty();
                $("#mySearchedBooks").empty();
                for (i = 0; i < data.length; i++) {
                    myBooks.append("<div class='card'>" +
                        "<div class='img'>"+
                        '<img  src="data:image/jpg;base64,'+data[i].picture+'"/>'+"</div>"+
                        "<div class='content'>"+
                        "<p class='title' style='text-transform: uppercase'> " + data[i].name + "</p>"           +
                        "<h5 class='copy'> " +'+'+ data[i].username + "</h5>"+
                        "<h5 class='copy' style='text-transform: capitalize'> " + data[i].author + "</h5>"+
                        "<h5 class='copy'> " + data[i].language + "</h5>"+
                        "<h5 class='copy'> " + data[i].comment + "</h5>"+
                        "<h5 class='copy'> " + data[i].region + ' ' + data[i].district +"</h5>"+
                         "</div></div>");
                }
            }
        });
    }});

//
$(document).ready(function(){
    $("#addMyBook").click(function(){
        var fd = new FormData();
        var files = $('#picture')[0].files[0];
        fd.append('file',files);
        if ($("#modalFormForValidate")) {
            console.log($("#modalFormForValidate"))
        }
        $.ajax({
            url:"/actionBook/addBook/"+$("#name").val()+"/" + $("#author").val()+"/"+parseInt($("#language").val())+ "/"+$("#comment").val(),
            type: "POST",
            data: fd,
            processData: false,
            contentType: false,
            success: function (result) {
                $('#modalFormForValidate')[0].reset();
            }
        });

    });


});

function checkAddressExist() {

    $.ajax(
        {
            type:"get",
            url: "/actionBook/region",
            success:function(response)
            {
                $("#addBook").modal('show');
                console.log(response);


            },error: function (jqXHR, exception) {
                console.log(jqXHR);
                errorGettingAddress(jqXHR, exception);
            }
        });
}

function errorGettingAddress(jqXHR, exception) {
    var msg = '';
    if (jqXHR.status === 0) {
        msg = 'Not connect.\n Verify Network.';
    } else if (jqXHR.status == 404) {
        msg = 'Requested page not found. [404]';
    }else if (jqXHR.status == null) {
        console.log(jqXHR.status);
        msg = 'Iltimos avval manzilni kiriting!';
    } else if (jqXHR.status == 500) {
        msg = 'Iltimos avval manzilni kiriting!';
    } else if (exception === 'parsererror') {
        msg = 'Requested JSON parse failed.';
    } else if (exception === 'timeout') {
        msg = 'Time out error.';
    } else if (exception === 'abort') {
        msg = 'Ajax request aborted.';
    } else {
        msg = 'Uncaught Error.\n' + jqXHR.responseText;
    }
    $('#checkAddress').html(msg);
}

(function() {
    'use strict';
    window.addEventListener('load', function() {
// Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
// Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

