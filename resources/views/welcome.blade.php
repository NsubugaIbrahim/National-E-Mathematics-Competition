@extends('layouts.app')

@section('content')
<head>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden; /* Prevent scrolling */
        }
        .full-page {
            display: flex;
    flex-direction: column;
    justify-content: center; /* Align vertically */
    align-items: center; /* Align horizontally */
    height: 100vh; /* Full viewport height */
    padding: 0;
    margin: 0;
            
        }
        .text-white {
            position: relative;
            top: 10px; 
        }
        .logo1 {
            position: relative;
            top: 0px; 
        }
        body::before {
            content: "";
            background-image: url('{{ asset('dark.jpg') }}');
            background-size: cover;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            filter: blur(3px); /* Adjust the blur radius as needed */
            z-index: -1;
        }
        .logo1 {
                display: flex;
                justify-content: center; /* Center horizontally */
                align-items: center; /* Center vertically */
                }
                .card1 {
    border-radius: 4px;
    background-color: rgba(157, 195, 230, 0.8);
    margin-bottom: 30px;
    /* opacity: 0.5; */
}
    </style>
</head>
    <div class="full-page section-image">    
        <h1 class="text-white text-center">{{ __('Welcome to National E Mathematics Competition Site') }}</h1>
        <div class="logo1"><img src="black/img/Logo4.png" alt="Logo" width="450" height="450"></div>
    </div>    
@endsection
