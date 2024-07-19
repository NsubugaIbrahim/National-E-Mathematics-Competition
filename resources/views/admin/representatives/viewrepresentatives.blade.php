@extends('layouts.app', ['pageSlug' => 'representative'])

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
        <h2 class="card-title">Representatives</h2> 

        <form action="{{ route('admin.representatives.search') }}" method="GET" class="form-inline mb-3">
            <div class="form-group mx-sm-3 mb-2">
                <label for="search" class="sr-only">Search Representatives</label>
                <input type="text" class="form-control" id="search" name="search" placeholder="Search representatives...">
            </div>
            <button type="submit" class="btn btn-primary mb-2">Search</button>
        </form>

                        @if (session('success'))
                            <div class="alert alert-success">{{session('success')}}</div>
                        @endif

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Representative Id</th>
                    <th>Representative Name</th>
                    <th>Representative Email</th>
                    <th>Registration number</th>
                </tr>
            </thead>
            <tbody>
                @foreach($representatives as $c)
                <tr>
                <td><a href="{{ route('representative.details', $c->representativeId) }}">{{$c->representativeId}}</a></td>
                    <td>{{$c->representativeName}}</td>
                    <td>{{$c->representativeEmail}}</td>
                    <td>{{$c->schoolRegNo}}</td>
                    <td>
                    <a href="{{ route('admin.representatives.editrepresentative', $c->representativeId) }}" class="btn btn-primary btn-sm">Edit</a>
                        <form action="{{ route('admin.representatives.destroy', $c->representativeId) }}" method="POST" style="display: inline-block">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this representative?')">Delete</button>
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
