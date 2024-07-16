<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Student Tables</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="../../plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../../dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini">
@include('layouts.header')

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">

      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Student List (Total : {{ $getRecord->total() }})</h1>
          </div>
          <div class="col-sm-6" style="text-align:right">
            <a href="{{url('admin/student/add')}}" class="btn btn-primary">Add new Student</a>
          </div>

        </div>
      </div><!-- /.container-fluid -->
    </section>

    <div class="row">
          <!-- left column -->



          <div class="col-md-12">
            
          <div class="col-md-12">
            <!-- general form elements -->
            <div class="card card-primary">
            <div class="card-header">
                <h3 class="card-title">Search Student</h3>
              </div>
              <form method="get" action="">
               <!-- Display Validation Errors -->
               @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
                <div class="card-body">

                  <div class="row">

                  
                  <div class="form-group col-md-2">
                    <label>First Name</label>
                    <input type="name" class="form-control" value="{{ Request::get('name')}}" name="name"  placeholder="First Name">
                  </div>

                  <div class="form-group col-md-2">
                    <label>Last Name</label>
                    <input type="last_name" class="form-control" value="{{ Request::get('last_name')}}" name="last_name"  placeholder="Last Name">
                  </div>

                  <div class="form-group col-md-2">
                    <label>Email</label>
                    <input type="text" class="form-control" value="{{ Request::get('email')}}" name="email"  placeholder="Email">
 
                  </div>

                  <div class="form-group col-md-2">
                    <label>Date</label>
                    <input type="date" class="form-control" value="{{ Request::get('date')}}" name="date" >
                    
                  </div>

                  <div class="form-group col-md-3 align-self-end">
                        <button class="btn btn-success" type="submit">Search</button>
                        <a href={{ url('admin/admin/list')}} class="btn btn-primary">Clear</a>
                    </div>
                       
                  </div>
            @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
            
        </div>
    </div>
    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-12">
            

            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Student List </h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body p-0" style="overflow:auto;">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th >#</th>
                      <th>Profile Pic</th>
                      <th>Name</th>
                      <th>Email</th>
                      <th>Admission Number</th>
                      <th>Class </th>
                      <th>Gender</th>
                      <th >Date Of Birth</th>
                      <th>Religion</th>
                      <th>Admission Date</th>
                      <th>Status</th>
                      <th>Created Date</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    @foreach($getRecord as $value)
                      <tr>
                        <td>{{ $value->id }}</td>
                        <td>
                          @if(!empty( $value->getProfile() ))
                          <img src="{{ $value->getProfile() }}" style="height: 50px; width:50px; border-radius: 50px;">
                          @endif
                        </td>
                        <td>{{ $value->full_name }}</td>
                        <td>{{ $value->email }}</td>
                        <td>{{ $value->admission_number	 }}</td>
                        <td >{{ $value->class_id }}</td>
                        <td>{{ $value->gender }}</td>
                        <td style="min-width: 100px;">{{ $value->date_of_birth }}</td>
                        <td>{{ $value->religion }}</td>
                        <td style="min-width: 100px;">{{ $value->admission_date	 }}</td>
                        <td>{{ ($value->status == 1) ? 'Active' : 'Inactive' }}</td>
                        <td style="min-width: 100px;">{{ $value->created_at->format('d-m-y H:i A')  }}</td>
                        <td style="min-width: 200px;">
                          <a href="{{ url('admin/student/edit/' .$value->id) }}" class="btn btn-primary btn-sm">Edit</a>
                          <a href="{{ url('admin/student/delete/' .$value->id) }}" class="btn btn-danger btn-sm">Delete</a>
                        </td>
                      </tr>
                    @endforeach
                  </tbody>
                </table>

                <div style="padding: 10px; float: right;">
                {!! $getRecord->appends(Illuminate\Support\Facades\Request::except('page'))->links() !!}
                <div>

              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
        
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
@include('layouts.footer')
<!-- jQuery -->
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="../../dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../../dist/js/demo.js"></script>
</body>
</html>

