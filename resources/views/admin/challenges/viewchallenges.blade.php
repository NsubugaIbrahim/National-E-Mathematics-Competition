@extends('layouts.app', ['pageSlug' => 'challenges'])

@section('content')
<html>
<head>
    <style>
         .alert.alert-success{
                    width: 50%;
                }
    </style>
</head>
<body>
    <div class="content">  
        <h2 class="card-title">Challenges </h2> 

            <form action="{{ route('admin.challenges.search') }}" method="GET" class="form-inline mb-3">
            <div class="form-group mx-sm-3 mb-2">
                <label for="search" class="sr-only">Search Challenges</label>
                <input type="text" class="form-control" id="search" name="search" placeholder="Search challenges...">
            </div>
            <button type="submit" class="btn btn-primary mb-2">Search</button>
            </form>

                        @if (session('success'))
                            <div class="alert alert-success">{{session('success')}}</div>
                        @endif

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Challenge ID</th>
                    <th>Number of questions</th>
                    <th>Duration</th>
                    <th>Start date</th>
                    <th>End date</th>
                    <th>Validity</th>
                </tr>
            </thead>
            <tbody>
                @foreach($challenges as $c)
                <tr>
                <td><a href="{{ route('challenge.details', $c->challengeId) }}">{{$c->challengeId}}</a></td>
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
                    <td>
                    <a href="{{ route('admin.challenges.editchallenge', $c->challengeId) }}" class="btn btn-primary btn-sm">Edit</a>
                        <form action="{{ route('admin.challenges.destroy', $c->challengeId) }}" method="POST" style="display: inline-block">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this challenge?')">Delete</button>
                        </form>
                    </td>
                </tr>
                @endforeach
            </tbody>                
        </table>
    </div>
</body>
</html>
@endsection
