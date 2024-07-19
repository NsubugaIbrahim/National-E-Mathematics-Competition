
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Add Participant</title>

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
            <h1>Add New Participant</h1>
          </div>
         
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <!-- left column -->
          <div class="col-md-12">
            <!-- general form elements -->
            <div class="card card-primary">
              
              <!-- /.card-header -->
              <!-- form start -->
              <form method="post" action="">
              {{ csrf_field()}}
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
                <div class="form-group">
                    <label>Participants UserName</label>
                    <input type="text" class="form-control" value="{{ old('username')}}" name="username" required placeholder="Participant's UserName">
                  </div>

                  <div class="form-group">
                    <label>Participant's First Name</label>
                    <input type="text" class="form-control" value="{{ old('firstName')}}" name="firstName" required placeholder="Participant First Name">
                  </div>

                  <div class="form-group">
                    <label>Participant's Last Name</label>
                    <input type="text" class="form-control" value="{{ old('lastName')}}" name="lastName" required placeholder="Participant last Name">
                  </div>

                  <div class="form-group">
                    <label>Participant's Email</label>
                    <input type="text" class="form-control" value="{{ old('email')}}" name="email" required placeholder="Participant Email">
                  </div>

                  <div class="form-group">
                    <label>Participant's D.O.B</label>
                    <input type="text" class="form-control" value="{{ old('dateOfBirth')}}" name="email" required placeholder="Date Of Birth">
                  </div>

                  <div class="form-group">
                    <label>School Registration No.</label>
                    <input type="text" class="form-control" value="{{ old('school_regNo')}}" name="school_regNo" required placeholder="School RegNo">
                  </div>

                  <div class="form-group">
                    <label>Status</label>
                    <input type="text" class="form-control" value="{{ old('status')}}" name="status" required placeholder="Participants Status">
                  </div>
                  
                <!-- /.card-body -->

                <div class="card-footer">
                  <button type="submit" class="btn btn-primary">Submit</button>
                </div>
              </form>
            </div>
            <!-- /.card -->

            
          <!--/.col (right) -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
 @include('layouts/footer');

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

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
