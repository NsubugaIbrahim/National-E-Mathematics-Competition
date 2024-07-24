@extends('layouts.app', ['page' => __('Schools'), 'pageSlug' => 'schools'])

@section('content')
<html>
    <head>
        <title>School Registration</title>
        <style>
                .alert.alert-success{
                    width: 50%;
                }
                .container{
                    width: 35%;
                    float: right;
                }
                .box{
                width: 60%;
            }
        </style>
    </head>
    <body>
        <div class="content">  
        <h1 class="card-title">School Registration form</h1>
        
        <div class="box">
            <div class="typography-line">
                <h3><p class="text-warning">
                Fill in the schools' details
                </p></h3>  
            </div>
        </div>

        

        <div class="container">
            <button id="addRow" class="btn btn-secondary"><i class="tim-icons icon-simple-add"></i>        Generate new row</button>

            <div class="alert alert-info alert-with-icon" data-notify="container">
                <button type="button" aria-hidden="true" class="close" data-dismiss="alert" aria-label="Close">
                    <i class="tim-icons icon-simple-remove"></i>
                </button>
                <span data-notify="message"> Click the button above to create a new row </span>
            </div>
        </div>

        <form method="post" action="/submit-schools">
        @csrf
        <table>
            <thead>
                <tr>
                    <th><h4>Registration number</h4></th>
                    <th><h4>School name</h4></th>
                    <th><h4>District</h4></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input type="text" name="regno[]" placeholder= "Regno" required></td>
                    <td><input type="text" name="name[]" placeholder= "School name" required></td>
                    <td><input type="text" name="district[]" placeholder= "District" required></td>
                </tr> 
            </tbody>
        </table>
        <br><br>
        <button id="" class="btn btn-secondary"> <i class="tim-icons icon-cloud-upload-94"></i>   Register School(s)</button>
        </form>
        <br><br>
                        @if (session('status'))
                            <div class="alert alert-success">{{session('status')}}</div>
                        @endif

        <a href="{{ route('schools') }}" class="btn btn-primary"><i class="tim-icons icon-notes"></i>   View Registered Schools</a>


        <script>
            document.getElementById('addRow').addEventListener('click', function() {
                const tableBody = document.querySelector('tbody');
                const newRow = document.createElement('tr');
                newRow.innerHTML = `
                    <td><input type="text" name="regno[]" placeholder= "Regno" required></td>
                    <td><input type="text" name="name[]" placeholder= "School name" required></td>
                    <td><input type="text" name="district[]" placeholder= "District" required></td>
                `;
                tableBody.appendChild(newRow);
            });
        </script>
    </body>
        
    </div>
</html>
@endsection

