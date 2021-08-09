import {
  Body,
  CacheInterceptor,
  CacheKey,
  Controller,
  Delete,
  Get,
  Logger,
  Param,
  Patch,
  Post,
  UseInterceptors,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { UserService } from './user.service';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { User } from './entities/user.entity';
import { GET_USER_CACHE_KEY, GET_USERS_CACHE_KEY } from '../cache/key.constant';

@Controller('user')
export class UserController {
  private readonly logger = new Logger(UserController.name);

  constructor(private readonly userService: UserService) {}

  @Post()
  @UsePipes(new ValidationPipe({ transform: true }))
  create(@Body() createUserDto: CreateUserDto): Promise<User> {
    this.logger.log(`Entering to create: createUserDto=${createUserDto}`);
    return this.userService.create(createUserDto);
  }

  @Get()
  @UseInterceptors(CacheInterceptor)
  @CacheKey(GET_USER_CACHE_KEY)
  findAll(): Promise<User[]> {
    this.logger.log('Entering to findAll');
    return this.userService.findAll();
  }

  @Get(':id')
  @UseInterceptors(CacheInterceptor)
  @CacheKey(GET_USERS_CACHE_KEY)
  findOne(@Param('id') id: string): Promise<User> {
    this.logger.log(`Entering to findOne: id=${id}`);
    return this.userService.findOne(+id);
  }

  @Patch(':id')
  @UsePipes(new ValidationPipe({ transform: true }))
  update(
    @Param('id') id: string,
    @Body() updateUserDto: UpdateUserDto,
  ): Promise<User> {
    this.logger.log(
      `Entering to update: id=${id}, updateUserDto=${updateUserDto}`,
    );
    return this.userService.update(+id, updateUserDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string): Promise<void> {
    this.logger.log(`Entering to remove: id=${id}`);
    return this.userService.remove(+id);
  }
}
