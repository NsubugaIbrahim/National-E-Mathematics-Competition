@extends('layouts.app', ['pageSlug' => 'challenges'])

@section('content')

<!DOCTYPE html>
<html>
<head>
    <title>Edit Challenge</title>
</head>
<body>
    <h1>Edit Challenge</h1>

    <form action="{{ route('admin.challenges.update', $challenge->challengeId) }}" method="POST">
        @csrf
        <table class="table tablesorter " id="">
                
                <tr><th><h4>Number of questions:</h4></th> <td><h4><input type="number" name="numberOfQuestions" id="numberOfQuestions" value="{{ $challenge->numberOfQuestions }}" required></h4></td><tr>
                <tr><th><h4>Duration:</h4></th> <td><h4> <input type="time" name="duration" id="duration" value="{{ $challenge->duration }}" required></h4></td><tr>
                <tr><th><h4>Start date:</h4></th> <td><h4><input type="date" name="startDate" id="startDate" value="{{ $challenge->startDate }}" required></h4></td><tr>
                <tr><th><h4>End date:</h4></th> <td><h4> <input type="date" name="endDate" id="endDate" value="{{ $challenge->endDate }}" required></h4></td><tr>
        </table>
        <div>
            <button type="submit" class="btn btn-primary">Update Challenge</button>
        </div>
    </form>

   
</body>
</html>

@endsection