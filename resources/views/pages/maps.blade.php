@extends('layouts.app', ['page' => __('Maps'), 'pageSlug' => 'maps'])

@section('content')
<html>
<head>
    <title>School Registration</title>
</head>
<body>
    <div class="content">  
    <h4 class="card-title">School Registration Table</h4>
    <p class="card-category">Fill in the schools' details</p>

    <button id="addRow"><i class="tim-icons icon-simple-add"></i>        Add School</button>

    <form method="post" action="/submit-schools">
    @csrf
    <table>
        <thead>
            <tr>
                <th>Registration number</th>
                <th>School name</th>
                <th>District</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><input type="text" name="regno"></td>
                <td><input type="text" name="name"></td>
                <td><input type="text" name="district"></td>
            </tr>
        </tbody>
    </table>
    <br><br>
    <button id=""> <i class="tim-icons icon-cloud-upload-94"></i>   Registers all Schools</button>
    </form>
    <br><br>
    

    <a href="{{ route('schools') }}" class="btn btn-primary"><i class="tim-icons icon-notes"></i>   View Registered Schools</a>


    <script>
        document.getElementById('addRow').addEventListener('click', function() {
            const tableBody = document.querySelector('tbody');
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td><input type="text" name="regno"></td>
                <td><input type="text" name="name"></td>
                <td><input type="text" name="district"></td>
            `;
            tableBody.appendChild(newRow);
        });
    </script>
</body>
    
</div>
</html>
@endsection

