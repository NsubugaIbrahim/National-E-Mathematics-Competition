<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Edit Participant</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <!-- AdminLTE -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css">
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
            <h1>Edit Participant</h1>
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
              <div class="card-body">
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
                <!-- form start -->
                <form method="post" action="">
                  @csrf
                  <div class="form-group">
                    <label for="username">Participant's UserName</label>
                    <input type="text" id="username" class="form-control" name="username" placeholder="UserName" value="{{ $getRecord->username }}">
                  </div>
                  
                  <div class="form-group">
                    <label for="firstName">Participant's First Name</label>
                    <input type="text" id="firstName" class="form-control" name="firstName" placeholder="First Name" value="{{ $getRecord->firstName }}">
                  </div>

                  <div class="form-group">
                    <label for="lastName">Participant's Last Name</label>
                    <input type="text" id="lastName" class="form-control" name="lastName" placeholder="Participant's Last Name" value="{{ $getRecord->lastName }}">
                  </div>

                  <div class="form-group">
                    <label for="email">Participants Email</label>
                    <input type="email" id="email" class="form-control" name="email" placeholder="Partcipant's Email" value="{{ $getRecord->email }}">
                  </div>

                  <div class="form-group">
                    <label for="dateOfBirth">Date Of Birth</label>
                    <input type="text" id="dateOfBirth" class="form-control" name="dateOfBirth" placeholder="dateOfBirth" value="{{ $getRecord->dateOfBirth }}">
                  </div>

                  <div class="form-group">
                    <label for="school_regNo">School Registration No.</label>
                    <input type="text" id="school_regNo" class="form-control" name="school_regNo" placeholder="School RegNo" value="{{ $getRecord->school_regNo }}">
                  </div>
                  <div class="form-group">
                    <label for="status">Status</label>
                    <input type="email" id="status" class="form-control" name="status" placeholder="status" value="{{ $getRecord->status }}">
                  </div>
                          
                  <div class="card-footer">
                    <button type="submit" class="btn btn-primary">Update</button>
                  </div>
                </form>
              </div><!-- /.card-body -->
            </div><!-- /.card -->
          </div><!--/.col (right) -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </section><!-- /.content -->
  </div><!-- /.content-wrapper -->

@include('layouts.footer')

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
  <!-- Control sidebar content goes here -->
</aside><!-- /.control-sidebar -->

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/demo.min.js"></script>
</body>
</html>
