import { CACHE_MANAGER, Inject, Injectable, Logger } from '@nestjs/common';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { User } from './entities/user.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Cache } from 'cache-manager';
import { GET_USER_CACHE_KEY, GET_USERS_CACHE_KEY } from '../cache/key.constant';
import { UserNotFoundException } from './exceptions/user-not-found.exception';

@Injectable()
export class UserService {
  private readonly logger = new Logger(UserService.name);
  constructor(
    @InjectRepository(User)
    private readonly userRepository: Repository<User>,
    @Inject(CACHE_MANAGER) private cacheManager: Cache,
  ) {}

  async create(createUserDto: CreateUserDto): Promise<User> {
    const user: User = this.userRepository.create(createUserDto);
    this.logger.log(`User created: createUserDto=${user}`);
    this.logger.log('Saving user');
    await this.userRepository.save(user);
    await this.clearCache();
    return user;
  }

  findAll(): Promise<User[]> {
    this.logger.log(`Getting all users`);
    return this.userRepository.find();
  }

  async findOne(id: number): Promise<User> {
    const user = await this.userRepository.findOne(id);
    if (user) {
      this.logger.log(`Got one user: id=${id}`);
      return user;
    }
    this.logger.log(`User not found: id=${id}`);
    throw new UserNotFoundException(id);
  }

  async update(id: number, updateUserDto: UpdateUserDto): Promise<User> {
    await this.userRepository.update(id, updateUserDto);
    const user = await this.userRepository.findOne(id);
    if (user) {
      this.logger.log(`Updated: id=${id}, updateUserDto=${updateUserDto}`);
      await this.clearCache();
      this.logger.log('Cache cleared');
      return user;
    }
    this.logger.log(`User not found: id=${id}`);
    throw new UserNotFoundException(id);
  }

  async remove(id: number): Promise<void> {
    const user = await this.userRepository.findOne(id);
    if (user) {
      await this.userRepository.delete(id);
      this.logger.log(`Deleted: id=${id}`);
      await this.clearCache();
      this.logger.log('Cache cleared');
      return;
    }
    this.logger.log(`User not found: id=${id}`);
    throw new UserNotFoundException(id);
  }

  async clearCache() {
    const keys: string[] = await this.cacheManager.store.keys();
    keys.forEach((key) => {
      if (
        key.startsWith(GET_USER_CACHE_KEY) ||
        key.startsWith(GET_USERS_CACHE_KEY)
      ) {
        this.cacheManager.del(key);
      }
    });
  }
}
