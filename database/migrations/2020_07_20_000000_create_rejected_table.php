<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('rejected', function (Blueprint $table) {
            $table->id();
            $table->string('username')->unique();
            $table->string('firstname');
            $table->string('lastname');
            $table->string('email');
            $table->string('password');
            $table->date('date_of_birth');
            $table->string('school_reg_no');
            $table->text('image_path')->nullable();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('rejected');
    }
};
