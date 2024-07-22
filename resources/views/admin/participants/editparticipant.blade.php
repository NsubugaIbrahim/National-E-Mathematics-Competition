@extends('layouts.app', ['pageSlug' => 'representatives'])

@section('content')

<!DOCTYPE html>
<html>
<head>
    <title>Edit Participant</title>
</head>
<body>
    <h1>Edit Participant</h1>

    <form action="{{ route('admin.participants.update', $participant->participantId) }}" method="POST">
        @csrf
        <table class="table tablesorter " id="">
                <tr><th><h4>Username:</h4></th> <td><h4><input type="text" name="username" id="username" value="{{ $participant->username }}" required></h4></td><tr>
                <tr><th><h4>First Name:</h4></th> <td><h4> <input type="text" name="firstName" id="firstName" value="{{ $participant->firstName }}" required></h4></td><tr>
                <tr><th><h4>Last Name:</h4></th> <td><h4><input type="text" name="lastName" id="lastName" value="{{ $participant->lastName }}" required></h4></td><tr>
                <tr><th><h4>Email:</h4></th> <td><h4><input type="text" name="email" id="email" value="{{ $participant->email }}" required></h4></td><tr>
                <tr><th><h4>Date of Birth:</h4></th> <td><h4> <input type="date" name="dateOfBirth" id="dateOfBirth" value="{{ $participant->dateOfBirth }}" required></h4></td><tr>
        </table>
        <div>
            <button type="submit" class="btn btn-primary">Update Participant</button>
        </div>
    </form>

   
</body>
</html>

@endsection