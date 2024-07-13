@extends('layouts.app', ['page' => __('Reps'), 'pageSlug' => 'reps'])

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
            .p.text-warning{
               margin-left:0px;
            }
            .box{
                width: 60%;
            }
            
        </style>
    </head>
    <body>
        <div class="content">  
        <h1 class="card-title">School Representatives' Table</h1>
        <div class="box">
            <div class="typography-line">
                <h4><p class="text-warning">
                    Fill in School Representatives' details to validate
                </p></h4>  
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


        <form method="post" action="/submit-representatives">
        @csrf
        <table border="1">
            <thead>
                <tr>
                    <th><h4>Representative name</h4></th>
                    <th><h4>Representative email</h4></th>
                    <th><h4>School registration number</h4></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input type="text" name="name[]" placeholder= "Name" required></td>
                    <td><input type="email" name="email[]" placeholder= "Email" required></td>
                    <td><input type="text" name="regno[]" style="width: 250px;" placeholder= "Regno" required></td>
                </tr>
            </tbody>
        </table>
        <br>
        <button id="" class="btn btn-secondary"><i class="tim-icons icon-cloud-upload-94"></i>   Validate all School representatives</button>
        </form>
                    @if (session('status'))
                        <div class="alert alert-success">{{session('status')}}</div>
                    @endif
        <br>
    <a href="{{ route('representatives') }}" class="btn btn-primary"><i class="tim-icons icon-paper"></i>   View Validated Representatives</a>

        
        <script>
            document.getElementById('addRow').addEventListener('click', function() {
                const tableBody = document.querySelector('tbody');
                const newRow = document.createElement('tr');
                newRow.innerHTML = `
                    <td><input type="text" name="name[]" placeholder= "Name" required></td>
                    <td><input type="email" name="email[]" placeholder= "Email" required></td>
                    <td><input type="text" name="regno[]" style="width: 250px;" placeholder= "Regno" required></td>
                `;
                tableBody.appendChild(newRow);
            });
        </script>
    </body>
    </div>
</html>
@endsection
