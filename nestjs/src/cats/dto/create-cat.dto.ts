import { IsNotEmpty, IsNumber, IsString } from 'class-validator';
export class CreateCatDto {
  @IsString()
  @IsNotEmpty()
  name: string;
  @IsNumber()
  @IsNotEmpty()
  age: number;
  @IsString()
  @IsNotEmpty()
  breed: string;
}
