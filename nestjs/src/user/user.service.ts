import { CACHE_MANAGER, Inject, Injectable } from '@nestjs/common';
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
  constructor(
    @InjectRepository(User)
    private readonly userRepository: Repository<User>,
    @Inject(CACHE_MANAGER) private cacheManager: Cache,
  ) {}

  async create(createUserDto: CreateUserDto): Promise<User> {
    const user = this.userRepository.create(createUserDto);
    await this.clearCache();
    return this.userRepository.save(user);
  }

  findAll(): Promise<User[]> {
    return this.userRepository.find();
  }

  findOne(id: number): Promise<User> {
    const user = this.userRepository.findOne(id);
    if (user) return user;
    throw new UserNotFoundException(id);
  }

  async update(id: number, updateUserDto: UpdateUserDto): Promise<User> {
    await this.userRepository.update(id, updateUserDto);
    const user = this.userRepository.findOne(id);
    if (user) {
      await this.clearCache();
      return user;
    }
    throw new UserNotFoundException(id);
  }

  async remove(id: number): Promise<void> {
    await this.userRepository.delete(id);
    await this.clearCache();
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
