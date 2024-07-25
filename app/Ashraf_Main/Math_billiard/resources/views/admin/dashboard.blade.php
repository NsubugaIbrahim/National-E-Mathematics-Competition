<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AdminLTE 3 | Dashboard</title>

<!-- Google Font: Source Sans Pro -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="{{ asset('dist/css/adminlte.min.css') }}">

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="{{ asset('plugins/fontawesome-free/css/all.min.css') }}">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Tempusdominus Bootstrap 4 -->
  <link rel="stylesheet" href="{{ asset('plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css') }}">
  <!-- iCheck -->
  <link rel="stylesheet" href="{{ asset('plugins/icheck-bootstrap/icheck-bootstrap.min.css') }}">
  <!-- JQVMap -->
  <link rel="stylesheet" href="{{ asset('plugins/jqvmap/jqvmap.min.css') }}">
  <!-- Theme style -->
  <link rel="stylesheet" href="{{ asset('dist/css/adminlte.min.css') }}">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="{{ asset('plugins/overlayScrollbars/css/OverlayScrollbars.min.css') }}">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="{{ asset('plugins/daterangepicker/daterangepicker.css') }}">
  <!-- summernote -->
  <link rel="stylesheet" href="{{ asset('plugins/summernote/summernote-bs4.min.css') }}">
<style>
    .todo-list {
        list-style: none;
        padding: 0;
    }
    .todo-list li {
        background-color: #f9f9f9;
        margin-bottom: 10px;
        padding: 15px;
        border-left: 3px solid #007bff;
        position: relative;
        overflow: hidden;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        transition: all 0.3s ease;
        cursor: move;
    }
    .todo-list li:hover {
        transform: translateY(-5px);
        box-shadow: 0 6px 12px rgba(0,0,0,0.2);
    }
    .todo-list li .text {
        margin-left: 10px;
        font-size: 1.1rem;
        font-weight: 500;
    }
    .todo-list li .time {
        margin-left: 10px;
        font-size: 0.8rem;
        color: #888;
    }
    .todo-list li .tools {
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
        opacity: 0;
        transition: opacity 0.3s ease;
    }
    .todo-list li:hover .tools {
        opacity: 1;
    }
    .todo-list li .tools i {
        font-size: 1.2rem;
        cursor: pointer;
        margin-left: 5px;
        color: #007bff;
        transition: color 0.3s ease;
    }
    .todo-list li .tools i:hover {
        color: #0056b3;
    }
</style>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Header -->
  @include('layouts.header')

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">

      <div class="row">
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
                <h3>150</h3>

                <p>New Orders</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3>53<sup style="font-size: 20px">%</sup></h3>

                <p>Bounce Rate</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>44</h3>

                <p>User Registrations</p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
              <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-danger">
              <div class="inner">
                <h3>65</h3>

                <p>Unique Visitors</p>
              </div>
              <div class="icon">
                <i class="ion ion-pie-graph"></i>
              </div>
              <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
        </div>

        
        <!-- TO DO List -->
        <div class="card card-primary mx-2">
          <div class="card-header">
            <h3 class="card-title ">To Do List</h3> 
          </div>
         
          <!-- /.card-header -->
          <div class="card-body">
            <div class="input-group mb-3">
              <input type="text" id="new-item-input" class="form-control" placeholder="New to-do item">
              <div class="input-group-append">
              <button id="add-item-button" class="btn btn-primary" type="button">
                  <i class="fas fa-plus" style="border-radius: 50%;"></i>
              </button>

              </div>
            </div>
            <ul class="todo-list" id="todo-list">
              <!-- Existing to-do items here -->
              <li data-id="1">
                <span class="text">Sample To-Do Item 1</span>
                <span class="time" data-timestamp="{{ date('Y-m-d H:i:s') }}">Just now</span>
                <span class="added-by">Added by: {{ Auth::user()->name }}</span>
                <div class="tools">
                    <i class="fas fa-check-circle mark-done"></i>
                    <i class="fas fa-trash delete-item"></i>
                </div>
              </li>
              
            </ul>
          </div>
          <!-- /.card-body -->
        </div>
        <!-- /.card -->
      </div>
      <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Footer -->
  @include('layouts.footer')

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="{{ asset('plugins/jquery/jquery.min.js') }}"></script>
<!-- Bootstrap 4 -->
<script src="{{ asset('plugins/bootstrap/js/bootstrap.bundle.min.js') }}"></script>
<!-- AdminLTE App -->
<script src="{{ asset('dist/js/adminlte.min.js') }}"></script>
<!-- jQuery UI -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<!-- Custom JavaScript for To-Do List -->
<script>
    $(document).ready(function() {
        // Initialize Sortable
        $('#todo-list').sortable({
            handle: '.text',
            update: function(event, ui) {
                // Update data-id attributes after sorting
                $('#todo-list li').each(function(index) {
                    $(this).attr('data-id', index + 1);
                });

                // Update time display for all items after sorting
                updateTimeDisplay();
            }
        });

        // Function to update time display for each item
        function updateTimeDisplay() {
            $('.todo-list li').each(function() {
                var timeElement = $(this).find('.time');
                var timeAdded = new Date(timeElement.data('timestamp'));
                var currentTime = new Date();
                var elapsed = currentTime - timeAdded; // Elapsed time in milliseconds

                // Convert elapsed time to a readable format
                var seconds = Math.floor(elapsed / 1000);
                var minutes = Math.floor(seconds / 60);
                var hours = Math.floor(minutes / 60);
                var days = Math.floor(hours / 24);

                var displayTime;
                if (days > 0) {
                    displayTime = days + ' days ago';
                } else if (hours > 0) {
                    displayTime = hours + ' hours ago';
                } else if (minutes > 0) {
                    displayTime = minutes + ' mins ago';
                } else {
                    displayTime = 'Just now';
                }

                timeElement.text(displayTime);
            });
        }

        // Add new item
        $('#add-item-button').click(function() {
            var newItemText = $('#new-item-input').val().trim();
            if (newItemText !== '') {
                var currentTime = new Date();
                var newItemHtml = `
                    <li data-id="${$('#todo-list li').length + 1}">
                        <span class="text">${newItemText}</span>
                        <span class="time" data-timestamp="${currentTime}">Just now</span>
                        <span class="added-by">Added by: {{ Auth::user()->name }}</span>
                        <div class="tools">
                            <i class="fas fa-check-circle mark-done"></i>
                            <i class="fas fa-trash delete-item"></i>
                        </div>
                    </li>
                `;
                $('#todo-list').append(newItemHtml);
                $('#new-item-input').val('');

                // Initialize Sortable again after adding a new item
                $('#todo-list').sortable('refresh');

                // Update time display for the newly added item
                updateTimeDisplay();
            }
        });

        // Mark item as done
        $(document).on('click', '.mark-done', function() {
            $(this).closest('li').toggleClass('done');
        });

        // Delete item
        $(document).on('click', '.delete-item', function() {
            $(this).closest('li').remove();
            // Re-index data-id attributes after deletion
            $('#todo-list li').each(function(index) {
                $(this).attr('data-id', index + 1);
            });
        });

        // Initial time display for existing items
        updateTimeDisplay();
    });
</script>

</body>
</html>
