@extends('layouts.app', ['pageSlug' => 'schools'])

@section('content')
<html>
<head>
</head>
<body>
    <div class="content">  
        <h4 class="card-title">Schools view</h4> 
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>School ID</th>    
                    <th>School Registration number</th>
                    <th>School name</th>
                    <th>District</th>
                </tr>
            </thead>
            <tbody>
                @foreach($schools as $c)
                <tr>
                    <td> <a href="">{{$c->schoolId}}</a></td>
                    <td>{{$c->schoolRegNo}}</td>
                    <td>{{$c->schoolName}}</td>
                    <td>{{$c->district}}</td>
                </tr>
                @endforeach
            </tbody>       
        </table>
    </div>
</body>
</html>
@endsection
