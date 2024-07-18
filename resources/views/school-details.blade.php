@extends('layouts.app', ['pageSlug' => 'challenges'])

@section('content')

<html>
<head>
  <title>School Details - {{$school->schoolName}}</title>
</head>
<body>
  <div class="content">
    <h2 class="card-title">School Details</h2>

    <div class="card ">
      <div class="table-responsive">
          <table class="table tablesorter " id="">
                      <tr><th><h4>School ID:</h4></th> <td><h4>{{$school->schoolId}}</h4></td><tr>
                      <tr><th><h4>Registration number:</h4></th> <td><h4>{{$school->schoolRegNo}}</h4></td><tr>
                      <tr><th><h4>School name:</h4></th> <td><h4>{{$school->schoolName}}</h4></td><tr>
                      <tr><th><h4>District:</h4></th> <td><h4>{{$school->district}}</h4></td><tr>
          </table>
        </div>
      </div>
  </div>
</body>
</html>
@endsection

