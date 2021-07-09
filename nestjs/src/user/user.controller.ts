import {
  Body,
  CacheInterceptor,
  CacheKey,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
  UseInterceptors,
} from '@nestjs/common';
import { UserService } from './user.service';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { User } from './entities/user.entity';
import { GET_USER_CACHE_KEY, GET_USERS_CACHE_KEY } from '../cache/key.constant';

@Controller('user')
export class UserController {
  constructor(private readonly userService: UserService) {}

  @Post()
  create(@Body() createUserDto: CreateUserDto): Promise<User> {
    return this.userService.create(createUserDto);
  }

  @Get()
  @UseInterceptors(CacheInterceptor)
  @CacheKey(GET_USER_CACHE_KEY)
  findAll(): Promise<User[]> {
    return this.userService.findAll();
  }

  @Get(':id')
  @UseInterceptors(CacheInterceptor)
  @CacheKey(GET_USERS_CACHE_KEY)
  findOne(@Param('id') id: string): Promise<User> {
    return this.userService.findOne(+id);
  }

  @Patch(':id')
  update(
    @Param('id') id: string,
    @Body() updateUserDto: UpdateUserDto,
  ): Promise<User> {
    return this.userService.update(+id, updateUserDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string): Promise<void> {
    return this.userService.remove(+id);
  }
}
