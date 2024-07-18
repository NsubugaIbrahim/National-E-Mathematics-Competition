<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Participant extends Model
{
    use HasFactory;
    
    protected $primaryKey = 'participantId';

    // If your primary key is not an integer, you should also set this property
    // public $incrementing = false;

    // If your primary key is not named 'id', you should disable the auto-incrementing behavior
    public $incrementing = false;
    public $timestamps = false; // Disable the automatic timestamps
    
    // If your primary key is a string
    // protected $keyType = 'string';
}
