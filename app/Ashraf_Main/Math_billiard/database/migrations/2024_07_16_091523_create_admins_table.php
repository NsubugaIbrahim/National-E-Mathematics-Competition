<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class MigrateUsersToNewTables extends Migration
{
    public function up()
    {
        Schema::create('admins', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->string('email')->unique();
            $table->string('password');
            $table->timestamps();
        });

        // Example data insertion
        $adminEmail = 'admin@gmail.com';

        // Check if the admin already exists before inserting
        $existingAdmin = DB::table('admins')->where('email', $adminEmail)->first();
        if (!$existingAdmin) {
            DB::table('admins')->insert([
                'name' => 'Admin',
                'email' => $adminEmail,
                'password' => Hash::make('password'), // Replace 'password' with the actual password
                'created_at' => now(),
                'updated_at' => now(),
            ]);
        }
    }

    public function down()
    {
        Schema::dropIfExists('admins');
    }
}
