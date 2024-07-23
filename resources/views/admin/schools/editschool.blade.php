@extends('layouts.app', ['pageSlug' => 'schools'])

@section('content')

<!DOCTYPE html>
<html>
<head>
    <title>Edit School</title>
</head>
<body>
    <h1>Edit School</h1>

    <form action="{{ route('admin.schools.update', $school->schoolId) }}" method="POST">
        @csrf
        <table class="table tablesorter " id="">
                <tr><th><h4>School Registration number:</h4></th> <td><h4><input type="text" name="schoolRegNo" id="schoolRegNo" value="{{ $school->schoolRegNo }}" required></h4></td><tr>
                <tr><th><h4>School name:</h4></th> <td><h4> <input type="text" name="schoolName" id="schoolName" value="{{ $school->schoolName }}" required></h4></td><tr>
                <tr><th><h4>District:</h4></th> <td><h4><input type="text" name="district" id="district" value="{{ $school->district }}" required></h4></td><tr>
        </table>
        <div>
            <button type="submit" class="btn btn-primary">Update School</button>
        </div>
    </form>

   
</body>
</html>

@endsection