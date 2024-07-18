@extends('layouts.app', ['pageSlug' => 'schools'])

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
        <h4 class="card-title">Schools view</h4> 

        <form action="{{ route('admin.schools.search') }}" method="GET" class="form-inline mb-3">
                <div class="form-group mx-sm-3 mb-2">
                    <label for="search" class="sr-only">Search Schools</label>
                    <input type="text" class="form-control" id="search" name="search" placeholder="Search schools...">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Search</button>
            </form>

                        @if (session('success'))
                            <div class="alert alert-success">{{session('success')}}</div>
                        @endif

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>School ID</th>    
                    <th>School Registration number</th>
                    <th>School name</th>
                    <th>District</th>
                </tr>
            </thead>
            <tbody>
                @foreach($schools as $c)
                <tr>
                    <td><a href="{{ route('school.details', $c->schoolId) }}">{{$c->schoolId}}</a></td>
                    <td>{{$c->schoolRegNo}}</td>
                    <td>{{$c->schoolName}}</td>
                    <td>{{$c->district}}</td>
                    <td>
                    <a href="{{ route('admin.schools.editschool', $c->schoolId) }}" class="btn btn-primary btn-sm">Edit</a>
                        <form action="{{ route('admin.schools.destroy', $c->schoolId) }}" method="POST" style="display: inline-block">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this school?')">Delete</button>
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
