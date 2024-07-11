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
                    <th>Registration number</th>
                    <th>School name</th>
                    <th>District</th>
                    <th>Representative Id</th>
                </tr>
            </thead>
            <tbody>
                @foreach($schools as $c)
                <tr>
                    <td>{{$c->schoolRegNo}}</td>
                    <td>{{$c->name}}</td>
                    <td>{{$c->district}}</td>
                    <td>{{$c->representativeId}}</td>
                </tr>
                @endforeach
            </tbody>       
        </table>
    </div>
</body>
</html>
@endsection
