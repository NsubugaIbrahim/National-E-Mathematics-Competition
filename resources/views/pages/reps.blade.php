@extends('layouts.app', ['page' => __('Reps'), 'pageSlug' => 'reps'])

@section('content')
<html>
    <head>
        <title>School Registration</title>
        <style>
            .alert.alert-success{
                width: 50%;
            }
        </style>
    </head>
    <body>
        <div class="content">  
        <h4 class="card-title">School Representatives' Table</h4>
        <p class="card-category">Add School Representatives to validate</p>

        <button id="addRow"><i class="tim-icons icon-simple-add"></i>        Add School representative</button>

        <form method="post" action="/submit-representatives">
        @csrf
        <table>
            <thead>
                <tr>
                    <th>Representative name</th>
                    <th>Representative email</th>
                    <th>School registration number</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input type="text" name="name[]" required></td>
                    <td><input type="email" name="email[]" required></td>
                    <td><input type="text" name="regno[]" required></td>
                </tr>
            </tbody>
        </table>
        <button id=""><i class="tim-icons icon-cloud-upload-94"></i>   Validate all School representatives</button>
        </form>
                    @if (session('status'))
                        <div class="alert alert-success">{{session('status')}}</div>
                    @endif
        <br><br>
    <a href="{{ route('representatives') }}" class="btn btn-primary"><i class="tim-icons icon-paper"></i>   View Validated Representatives</a>

        
        <script>
            document.getElementById('addRow').addEventListener('click', function() {
                const tableBody = document.querySelector('tbody');
                const newRow = document.createElement('tr');
                newRow.innerHTML = `
                    <td><input type="text" name="name[]" required></td>
                    <td><input type="email" name="email[]" required></td>
                    <td><input type="text" name="regno[]" required></td>
                `;
                tableBody.appendChild(newRow);
            });
        </script>
    </body>
    </div>
</html>
@endsection
