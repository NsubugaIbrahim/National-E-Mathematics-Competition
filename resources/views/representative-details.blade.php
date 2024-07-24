@extends('layouts.app', ['pageSlug' => 'representative'])

@section('content')

<html>
<head>
  <title>Representative Details - {{$representative->representativeName}}</title>
</head>
<body>
  <div class="content">
    <h2 class="card-title">Representative Details</h2>

    <div class="card ">
      <div class="table-responsive">
          <table class="table tablesorter " id="">
                      <tr><th><h4>Representative ID:</h4></th> <td><h4>{{$representative->representativeId}}</h4></td><tr>
                      <tr><th><h4>Representative name:</h4></th> <td><h4>{{$representative->representativeName}}</h4></td><tr>
                      <tr><th><h4>Representative Email:</h4></th> <td><h4>{{$representative->representativeEmail}}</h4></td><tr>
                      <tr><th><h4>Registration number:</h4></th> <td><h4>{{$representative->schoolRegNo}}</h4></td><tr>
          </table>
        </div>
      </div>
  </div>
</body>
</html>
@endsection