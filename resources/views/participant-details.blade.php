@extends('layouts.app', ['pageSlug' => 'representative'])

@section('content')

<html>
  <head>
    <title>Participant Details - {{$participant->username}}</title>
    <style>
      .un{
        display: flex;
        justify-content: center; /* Center horizontally */
      }
    </style>
  </head>
<body>
  <div class="content">
    <h2 class="card-title">Participant Details</h2>
    <div class="row">
    <div class="col-md-6">
    <div class="card ">
    <img src="{{ asset('participant-images/' . basename($participant->imageFilePath)) }}" alt="Participant Image" width="520" height="372">
    <div class="un">
      <tr><h4>Username: {{$participant->username}}</h4><tr>
    </div>    
</div>
    </div>   
    <div class="col-md-6">
    <div class="card ">
      <div class="table-responsive">
          <table class="table tablesorter " id="">
                      <tr><th><h4>Participant ID:</h4></th> <td><h4>{{$participant->participantId}}</h4></td><tr>
                      <tr><th><h4>First Name:</h4></th> <td><h4>{{$participant->firstName}}</h4></td><tr>
                      <tr><th><h4>Last Name:</h4></th> <td><h4>{{$participant->lastName}}</h4></td><tr>
                      <tr><th><h4>Email:</h4></th> <td><h4>{{$participant->email}}</h4></td><tr>
                      <tr><th><h4>Date of Birth:</h4></th> <td><h4>{{$participant->dateOfBirth}}</h4></td><tr>
                      <tr><th><h4>Registration number:</h4></th> <td><h4>{{$participant->schoolRegno}}</h4></td><tr>                     
          </table>
        </div>
      </div>
  </div>
  </div>
  </div>
</body>
</html>
@endsection