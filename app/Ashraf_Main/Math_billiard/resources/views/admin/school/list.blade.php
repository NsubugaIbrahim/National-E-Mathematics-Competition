<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Schools</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="../../plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../../dist/css/adminlte.min.css">
</head>
< class="hold-transition sidebar-mini">
@include('layouts.header')

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">

      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Schools List (Total : {{ $getRecord->total() }})</h1>
          </div>
          <div class="col-sm-6" style="text-align:right">
            <a href="{{url('admin/school/add')}}" class="btn btn-primary">Add new school</a>
          </div>

        </div>
      </div><!-- /.container-fluid -->
    </section>

    <div class="row">
          <!-- left column -->



          <div class="col-md-12">
            <!-- general form elements -->
            <div class="card card-primary mx-2">
            <div class="card-header ">
                <h3 class="card-title">Search School</h3>
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

                  
                  <div class="form-group col-md-3">
                    <label>School Name</label>
                    <input type="name" class="form-control" value="{{ Request::get('name')}}" name="name"  placeholder="School Name">
                  </div>

                  <div class="form-group col-md-3">
                    <label>District</label>
                    <input type="district" class="form-control" value="{{ Request::get('district')}}" name="district"  placeholder="District">
                  </div>

                  <div class="form-group col-md-3">
                    <label>Representative Name</label>
                    <input type="representative_name" class="form-control" value="{{ Request::get('representative_name')}}" name="representative_name"  placeholder="Representative Name">
                  </div>
                 
                  <div class="form-group col-md-3 align-self-end">
                        <button class="btn btn-success" type="submit">Search</button>
                        <a href={{ url('admin/school/list')}} class="btn btn-primary">Clear</a>
                    </div>
                       
                  </div>
                  
                <!-- /.card-body -->

                
              </form>
            </div>
            <!-- /.card -->

            
          <!--/.col (right) -->
        </div>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-12">
            

            <div class="card">
              <div class="card-header">
                <h3 class="card-title">School List </h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body p-0">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>School Name</th>
                      <th>District</th>
                      <th>School RegNo.</th>
                      <th>Representative Name</th>
                      <th>Representative Email</th>
                      <!-- <th>Created By</th>-->
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                  @foreach($getRecord as $value)
                      <tr>
                          <td>{{ $value->id }}</td>
                          <td>{{ $value->name }}</td>
                          <td>{{ $value->district }}</td>
                          <td>{{ $value->school_regNo }}</td>
                          <td>{{ $value->representative_name }}</td>
                          <td>{{ $value->representative_email }}</td>
                          <!-- <td>{{ $value->created_name }}</td> -->
                          <!-- <td>{{ date('d-m-Y H:i A', strtotime($value->created_at)) }}</td> Corrected date format -->
                          <td>
                              <a href="{{ url('admin/school/edit/' .$value->id) }}" class="btn btn-primary">Edit</a>
                              <a href="{{ url('admin/school/delete/' .$value->id) }}" class="btn btn-danger">Delete</a>
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
 </div>
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

