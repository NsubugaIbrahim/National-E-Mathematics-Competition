@extends('layouts.app', ['pageSlug' => 'representative'])

@section('content')
<html>
<head>
</head>
<body>
    <div class="content">  
        <h4 class="card-title">Representatives table</h4> 
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Representative Id</th>
                    <th>Representative Name</th>
                    <th>Representative Email</th>
                    <th>Registration number</th>
                </tr>
            </thead>
            <tbody>
                @foreach($representatives as $c)
                <tr>
                    <td>{{$c->representativeId}}</td>
                    <td>{{$c->representativeName}}</td>
                    <td>{{$c->representativeEmail}}</td>
                    <td>{{$c->schoolRegNo}}</td>
                </tr>
                @endforeach
            </tbody>       
        </table>
    </div>
</body>
</html>
@endsection
