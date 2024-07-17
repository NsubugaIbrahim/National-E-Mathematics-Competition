@extends('layouts.app', ['pageSlug' => 'challenges'])

@section('content')

<html>
<head>
  <title>Challenge Details - {{$challenge->challengeName}}</title>
</head>
<body>
  <div class="content">
    <h2 class="card-title">Challenge Details</h2>

    <div class="card ">
    <div class="table-responsive">
    <table class="table tablesorter " id="">
                <tr><th><h4>Challenge ID:</h4></th> <td><h4>{{$challenge->challengeId}}</h4></td><tr>
                <tr><th><h4>Number of questions:</h4></th> <td><h4>{{$challenge->numberOfQuestions}}</h4></td><tr>
                <tr><th><h4>Duration:</h4></th> <td><h4>{{$challenge->duration}}</h4></td><tr>
                <tr><th><h4>Start date:</h4></th> <td><h4>{{$challenge->startDate}}</h4></td><tr>
                <tr><th><h4>End date:</h4></th> <td><h4>{{$challenge->endDate}}</h4></td><tr>
                <tr><th><h4>Availability:</h4></th> <td class="availability"><h4>
                  @if (strtotime($challenge->startDate) <= strtotime('now') && strtotime('now') <= strtotime($challenge->endDate))
                  ✓
                @else
                  ✗
                @endif
              </h4></td><tr>
        </table>
        </div>
        </div>
  </div>
</body>
</html>
@endsection

