@extends('layouts.app', ['pageSlug' => 'representatives'])

@section('content')

<!DOCTYPE html>
<html>
<head>
    <title>Edit Representative</title>
</head>
<body>
    <h1>Edit Representative</h1>

    <form action="{{ route('admin.representatives.update', $representative->representativeId) }}" method="POST">
        @csrf
        <table class="table tablesorter " id="">
                <tr><th><h4>Representative Name:</h4></th> <td><h4><input type="text" name="representativeName" id="representativeName" value="{{ $representative->representativeName }}" required></h4></td><tr>
                <tr><th><h4>Representative Email:</h4></th> <td><h4> <input type="text" name="representativeEmail" id="representativeEmail" value="{{ $representative->representativeEmail }}" required></h4></td><tr>
                <tr><th><h4>Registration number:</h4></th> <td><h4><input type="text" name="schoolRegNo" id="schoolRegNo" value="{{ $representative->schoolRegNo }}" required></h4></td><tr>
        </table>
        <div>
            <button type="submit" class="btn btn-primary">Update Representative</button>
        </div>
    </form>

   
</body>
</html>

@endsection