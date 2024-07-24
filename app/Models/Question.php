<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
  public $timestamps = false;
  
  protected $table = 'questions';
  protected $fillable = ['questionId', 'questionText'];

  
}

