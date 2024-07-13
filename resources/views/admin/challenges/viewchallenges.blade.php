@extends('layouts.app', ['pageSlug' => 'challenges'])

@section('content')
<html>
<head>
</head>
<body>
    <div class="content">  
        <h4 class="card-title">Challenges view</h4> 
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Challenge ID</th>
                    <th>Number of questions</th>
                    <th>Duration</th>
                    <th>Start date</th>
                    <th>End date</th>
                    <th>Availability</th>
                </tr>
            </thead>
            <tbody>
                @foreach($challenges as $c)
                <tr>
                    <td><a href="#">{{$c->challengeId}}</a></td>
                    <td>{{$c->numberOfQuestions}}</td>
                    <td>{{$c->duration}}</td>
                    <td>{{$c->startDate}}</td>
                    <td>{{$c->endDate}}</td>
                    <td class="availability">
                        @if (strtotime($c->startDate) <= strtotime('now') && strtotime('now') <= strtotime($c->endDate))
                            ✓
                        @else
                            ✗
                        @endif
                    </td>
                </tr>
                @endforeach
            </tbody>                
        </table>
    </div>
</body>
</html>
@endsection
